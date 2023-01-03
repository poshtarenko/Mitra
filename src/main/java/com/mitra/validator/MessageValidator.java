package com.mitra.validator;

import com.mitra.dto.ChatDto;
import com.mitra.dto.MessageDto;
import com.mitra.dto.ProfileDto;
import com.mitra.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageValidator {

    public void checkMessage(MessageDto message) throws ValidationException {
        List<Error> errors = new ArrayList<>();

        checkMessageText(message.getMessage()).ifPresent(errors::add);
        checkChat(message.getChat()).ifPresent(errors::add);
        checkSender(message.getSender()).ifPresent(errors::add);
        checkTime(message.getTime()).ifPresent(errors::add);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    public Optional<Error> checkMessageText(String msgText) {
        if (msgText.length() > 1000 || msgText.length() < 1) {
            return Optional.of(Error.of("INVALID MESSAGE",
                    "Повідомлення не може містити більше 1000 символів"));
        }
        return Optional.empty();
    }

    public Optional<Error> checkChat(ChatDto chatDto) {
        if (chatDto == null || chatDto.getId() == null) {
            return Optional.of(Error.of("CHAT IS NULL",
                    "Повідомлення повинно бути закріплене за чатом"));
        }
        return Optional.empty();
    }

    public Optional<Error> checkSender(ProfileDto sender) {
        if (sender == null || sender.getId() == null) {
            return Optional.of(Error.of("SENDER IS NULL",
                    "Повідомлення з невизначеним відправником"));
        }
        return Optional.empty();
    }

    public Optional<Error> checkTime(LocalDateTime time) {

        long timeDifference = Math.abs(ChronoUnit.MINUTES.between(time, LocalDateTime.now()));

        if (timeDifference > 30) {
            return Optional.of(Error.of("MESSAGE IS OVERDUE",
                    "Повідомлення надіслано надто пізно"));
        }
        return Optional.empty();
    }


}
