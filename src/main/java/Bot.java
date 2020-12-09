import Weather.Weather;
import Weather.Model;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String text, String[] buttonsName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);
        try {

            setButtons(sendMessage, buttonsName);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        String weather = "/weather";
        String[] firstButtonsName = new String[]{"/weather", "/news",};
        String[] exitButtonName = new String[]{"/exit"};
        String[] newsButtonName = new String[]{"/region","/rubric"};
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals(weather)) {
                switch (message.getText()) {
                    case "/weather":
                        sendMsg(message, "Введине название интересуещего Вас города", exitButtonName);
                        break;
                    case "/exit":
                        sendMsg(message, "Назад", firstButtonsName);
                        break;
                    default:
                        try {
                            sendMsg(message, Weather.getWeather(message.getText(), model), firstButtonsName);
                        } catch (IOException e) {
                            sendMsg(message, "Город не найден!", firstButtonsName);
                        }
                }
            } else {
                switch (message.getText()) {
                    case "/news":
                        sendMsg(message, "Пока всего два дальнейших движения....", newsButtonName);
                        break;
                    case "/exit":
                        sendMsg(message, "Назад", firstButtonsName);
                        break;
                    case "/rubric":
                        sendMsg(message, "Работаю над этим...", exitButtonName);
                        break;
                    case "/region":
                        sendMsg(message, "Тоже работаю над этим...", exitButtonName);
                        break;
                    default:
                        sendMsg(message, "Отстань", exitButtonName);

                }
            }
//            switch (message.getText()) {
//                case "/weather":
//                    sendMsg(message, "Введине название интересуещего Вас города", exitButtonName);
//                    break;
//                case "/news":
//                    sendMsg(message, "Пока всего два дальнейших движения....", exitButtonName);
//                    break;
//                case "/exit":
//                    sendMsg(message, "Назад", firstButtonsName);
//                    break;
//                case "/rubric":
//                    break;
//                case "/region":
//                    break;
//
//                    default:
//                        try {
//                            sendMsg(message, Weather.getWeather(message.getText(), model), firstButtonsName);
//                        } catch (IOException e) {
//                            sendMsg(message, "Город не найден!", firstButtonsName);
//                        }
//                }
            }
        }
        public void setButtons (SendMessage sendMessage, String[]buttonsName){
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();

            for (int i = 0; i < buttonsName.length; i++) {
                keyboardFirstRow.add(new KeyboardButton(buttonsName[i]));
                //keyboardFirstRow.add(new KeyboardButton("/news"));
            }


            keyboardRowList.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);

        }

        public String getBotUsername () {
            return "Arsedon_bot";
        }

        public String getBotToken () {
            return "1492966314:AAETZ7WRys1bmAE36KO7-MfIXb3_MYsX8io";
        }
    }


