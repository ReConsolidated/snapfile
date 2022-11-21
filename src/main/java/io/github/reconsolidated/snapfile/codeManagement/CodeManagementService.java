package io.github.reconsolidated.snapfile.codeManagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@Slf4j
public class CodeManagementService {
    public static int CODE_EXPIRATION_TIME;

    private final CodeRepository codeRepository;
    private final int codeLength;
    private int maxCodeValue;
    private final List<Integer> codesAvailable = new ArrayList<>();
    private final Random random = new Random();

    public CodeManagementService(CodeRepository codeRepository,
                                 @Value("${code_length}") int codeLength,
                                 @Value("${code_expiration_time}") int codeExpirationTime) {
        this.codeRepository = codeRepository;
        this.codeLength = codeLength;
        this.maxCodeValue = (int) Math.pow(10, codeLength);

        for (int i = 0; i < maxCodeValue; i++) {
            codesAvailable.add(i);
        }
        CODE_EXPIRATION_TIME = codeExpirationTime;
    }

    // clean expired codes
    @Scheduled(fixedRateString = "${code_expiration_check_time}000")
    public void cleanInactiveCodes() {
        long startTime = System.currentTimeMillis();
        log.info("Cleaning inactive codes");
        var codes = codeRepository.findAll();
        for (var code : codes) {
            if (code.getSendTime() + 1000L * CODE_EXPIRATION_TIME < System.currentTimeMillis()) {
                codeRepository.delete(code);
                File file = new File(code.getFilePath());
                if (file.delete()) {
                    log.info("Deleted file: " + code.getFilePath());
                } else {
                    log.warn("Deleted code, but couldn't delete file: " + code.getFilePath());
                }
                codesAvailable.add(Integer.parseInt(code.getCode()));
                restoreCodeAvailability(code.getCode());
            }
        }
        log.info("Done cleaning inactive codes, time: %dms".formatted(System.currentTimeMillis() - startTime));
    }

    public void addCode(CodeInstance instance) {
        if (codeRepository.findByCode(instance.getCode()).isPresent()) {
            throw new CodeAlreadyExistsException(instance.getCode());
        }
        codeRepository.save(instance);
    }

    public void restoreCodeAvailability(String code) {
        int intCode = Integer.parseInt(code);
        if (!codesAvailable.contains(intCode)) {
            codesAvailable.add(intCode);
        }
    }

    public String getAvailableCode(int attemptsLeft) {
        if (codesAvailable.size() == 0) {
            throw new NoAvailableCodesException();
        }
        int code = getRandomCode();
        if (codeRepository.findByCode(String.valueOf(code)).isPresent()) {
            codesAvailable.remove(Integer.valueOf(code));
            if (attemptsLeft == 0) {
                throw new NoAvailableCodesException();
            }
            return getAvailableCode(attemptsLeft-1);
        }
        codesAvailable.remove(Integer.valueOf(code));
        return formatCode(code);
    }

    private String formatCode(int code) {
        return String.format("%0" + codeLength + "d", code);
    }

    private int getRandomCode() {
        return codesAvailable.get(random.nextInt(codesAvailable.size()));
    }

    private boolean isCodeUsed(int code) {
        return codeRepository.findByCode(formatCode(code)).isPresent();
    }

    public Optional<CodeInstance> getCodeInstance(String code) {
        return codeRepository.findByCode(code);
    }
}
