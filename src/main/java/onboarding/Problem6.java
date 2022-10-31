package onboarding;

import java.util.*;
import java.util.stream.Collectors;

public class Problem6 {
    private static final int DUPLICATED_COUNT = 2;
    private static final int MIN_EMAIL_SIZE = 11;
    private static final int MAX_EMAIL_SIZE = 20;
    private static final int MIN_NICKNAME_SIZE = 1;
    private static final int MAX_NICKNAME_SIZE = 20;

    public static List<String> solution(List<List<String>> forms) {
        Map<String, List<String>> nicknames = createNicknameMap(forms);
        Set<String> duplicateEmails = getDuplicateEmails(nicknames);

        return sortAnswer(duplicateEmails);
    }

    private static List<String> sortAnswer(Set<String> duplicateEmails) {
        List<String> answer;
        answer = duplicateEmails.stream()
                .sorted()
                .collect(Collectors.toList());
        return answer;
    }


    private static Set<String> getDuplicateEmails(Map<String, List<String>> nicknameDict) {
        Set<String> duplicatedEmails = new HashSet<>();

        nicknameDict.forEach((nickname, emails) -> {
            if (emails.size() >= DUPLICATED_COUNT) {
                duplicatedEmails.addAll(emails);
            }
        });

        return duplicatedEmails;
    }

    private static Map<String, List<String>> createNicknameMap(List<List<String>> forms) {
        Map<String, List<String>> nicknames = new HashMap<>();
        for (List<String> form : forms) {
            String email = form.get(0);
            String nickname = form.get(1);
            if (!isValidEmail(email) || !isValidNickname(nickname)){
                continue;
            }

            List<String> twoLetterNicknames = splitTwoLetter(nickname);
            storeEmails(nicknames, email, twoLetterNicknames);
        }
        return nicknames;
    }

    private static List<String> splitTwoLetter(String nickname) {
        List<String> twoLetterNicknames = new ArrayList<>();

        for (int i = 0; i < nickname.length() - 1; i++) {
            String twoLetterNickname = nickname.substring(i, i + 2);
            twoLetterNicknames.add(twoLetterNickname);
        }

        return twoLetterNicknames;
    }

    private static void storeEmails(Map<String, List<String>> nicknames, String email, List<String> splitNicknames) {
        for (String nickname : splitNicknames) {
            List<String> emails = new ArrayList<>();
            if (nicknames.containsKey(nickname)) {
                emails = nicknames.get(nickname);
            }
            emails.add(email);
            nicknames.put(nickname, emails);
        }
    }

    private static boolean isValidEmail(String email) {
        String emailExp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@email.com";
        if (email.length() < MIN_EMAIL_SIZE || email.length() >= MAX_EMAIL_SIZE) {
            return false;
        }

        if (!email.matches(emailExp)) {
            return false;
        }

        return true;
    }

    private static boolean isValidNickname(String nickname) {
        String nicknameExp = "^[ㄱ-ㅎ가-힣]*$";
        if (nickname.length() < MIN_NICKNAME_SIZE || nickname.length() >= MAX_NICKNAME_SIZE) {
            return false;
        }

        if (!nickname.matches(nicknameExp)) {
            return false;
        }

        return true;
    }
}
