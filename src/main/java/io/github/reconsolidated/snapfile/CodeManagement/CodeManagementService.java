package io.github.reconsolidated.snapfile.CodeManagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CodeManagementService {
    @Value("${code_length}")
    private final int CODE_LENGTH = 6;
    private final int maxCodeValue = (int) Math.pow(10, CODE_LENGTH);
    private final List<Integer> codesAvailable = new ArrayList<>();
    private final Set<CodeInstance> codeInstances = new HashSet<>();
    private final Random random = new Random();

    public CodeManagementService() {
        for (int i = 0; i < maxCodeValue; i++) {
            codesAvailable.add(i);
        }
    }

    public boolean addCode(CodeInstance instance) {
        if (codeInstances.contains(instance)) {
            throw new CodeAlreadyExistsException(instance.getCode());
        }
        return codeInstances.add(instance);
    }

    public void restoreCodeAvailability(String code) {
        int intCode = Integer.parseInt(code);
        if (!codesAvailable.contains(intCode)) {
            codesAvailable.add(intCode);
        }
    }

    public String getAvailableCode() {
        if (codesAvailable.size() == 0) {
            throw new NoAvailableCodesException();
        }
        int code = getRandomCode();
        codesAvailable.remove(code);
        return formatCode(code);
    }

    private String formatCode(int code) {
        return String.format("%0" + CODE_LENGTH + "d", code);
    }

    private int getRandomCode() {
        return codesAvailable.get(random.nextInt(codesAvailable.size()));
    }

    private boolean isCodeUsed(int code) {
        return codeInstances.contains(new CodeInstance(String.valueOf(code), "", ""));
    }

    public Optional<CodeInstance> getCodeInstance(String code) {
        for (CodeInstance instance : codeInstances) {
            if (instance.getCode().equals(code)) {
                return Optional.of(instance);
            }
        }
        return Optional.empty();
    }
}
