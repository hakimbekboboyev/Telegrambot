import com.darkprograms.speech.translator.GoogleTranslate;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new Main());
    }
    int i = 0;
    ArrayList<BotUsers> botUsers = new ArrayList<>();


    public String getBotUsername() {
        return "@chet_tili_tarjimonbot";
    }

    public String getBotToken() {
        return "5952465775:AAEAqqHRv8I9A98n-LucpnRb7L7s7YdTNwM";
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {




        if (update.hasMessage()) {
            System.out.println(update.getMessage().getFrom().getFirstName()+" "+update.getMessage().getFrom().getUserName()+"-->"+update.getMessage().getText());





            BotUsers user = returnUser(update.getMessage().getChatId().toString(), update.getMessage().getText());

            System.out.println(user.getStep());
            /*if(user.getStep()==null && user.getLanguage()==null){
                user.setStep(BotSteps.SELECT_LANG);
            }*/

            Message message = update.getMessage();
            String chatId = user.getChatId();


            if (message.getText().equals(BotSteps.START)) {




                sendMesText(chatId, "Assalomu aleykum " + message.getFrom().getFirstName() + " botimizga xush kelibsiz!");

                user.setStep(BotSteps.SELECT_LANG);
                user.setLanguage("");




            }
            if (user.getStep().equals(BotSteps.SELECT_LANG)) {
                inlineButton(chatId);
                //user.setStep(BotSteps.SELECT_LANG);
            }
            if(message.getText().equals(BotSteps.ADMIN)){

                sendMesText(chatId,"Admin-> @codemylove");
                user.setLanguage("");
                //user.setStep(update.getCallbackQuery().getData());

            }
            if(message.getText().equals(BotSteps.SELECT_LANG)){


                inlineButton(chatId);
                user.setStep("");
                user.setLanguage("");
            }
            if (message.getText().equals(BotSteps.HELP)){

                sendMesText(chatId,"⁉️Botimizdan foydalanish juda ham oson!   \uD83D\uDC49  /select_lang \uD83D\uDC48 tugmachasi orqali tilni tanlaysiz va botimiz istalgan so'zni siz tanlangan tilda tarjima qilib beradi!!");
                user.setLanguage("");
                //user.setStep(update.getCallbackQuery().getData());
            }


            if (user.getLanguage().equals(BotSteps.ENG)) {

                String translate = GoogleTranslate.translate("eng", message.getText());
                sendMesText(chatId, "\uD83C\uDDFA\uD83C\uDDF8English->"+translate);

            }
            if (user.getLanguage().equals(BotSteps.UZ)) {

                String translate = GoogleTranslate.translate("uz",message.getText());
                sendMesText(chatId, "\uD83C\uDDFA\uD83C\uDDFFUzbek->"+translate);

            }if (user.getLanguage().equals(BotSteps.RU)){
                String translate = GoogleTranslate.translate("ru",message.getText());
                sendMesText(chatId, "\uD83C\uDDF7\uD83C\uDDFArus->"+translate);
            }






        }else if(update.hasCallbackQuery()){


            BotUsers user = returnUser(update.getCallbackQuery().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getText());
            //BotUsers user = new BotUsers();

            /*for (BotUsers user1: botUsers ) {
                if(user1.equals(botUsers)){
                    user = user1;
                    user.setChatId(update.getCallbackQuery().getFrom().getId().toString());
                }

            }*/
            String data = update.getCallbackQuery().getData();
            String chatId = user.getChatId();


            if (data.equals(BotSteps.ENG)) {

                sendMesText(chatId, "Please enter a word and we will translate it into English: ");

                user.setLanguage(BotSteps.ENG);
                user.setStep(user.getLanguage());


            }
            else if(data.equals(BotSteps.UZ)){

                sendMesText(chatId, "Marhamat so'z kiritng o'zbek tiliga tarjima qilamiz:");

                user.setLanguage(BotSteps.UZ);
                user.setStep(user.getLanguage());

            }
            else if(data.equals(BotSteps.RU)) {

                sendMesText(chatId, "Введите слово и мы переведем его на русский язык: ");

                user.setLanguage(BotSteps.RU);
                user.setStep(user.getLanguage());
            }


        }




    }

    private BotUsers returnUser(String id, String message) {
        for (BotUsers user: botUsers ) {
            if(user.getChatId().equals(id)){
                System.out.println(user.getChatId().equals(id));


                return user;
            }


        }


        BotUsers users = new BotUsers();
        users.setChatId(id);

        //users.setStep("/start");
        if(message.equals(BotSteps.START)){
            botUsers.add(users);
            users.setStep(BotSteps.START);
        }
        else if(message.equals(BotSteps.SELECT_LANG)){
            //users.setStep(BotSteps.SELECT_LANG);
            botUsers.add(users);
            /*sendMesText(id,"/start buyrug'ini yuboring");*/
            users.setStep(BotSteps.START);

        }
        else if(message.equals(BotSteps.HELP)){
            //users.setStep(BotSteps.SELECT_LANG);
            botUsers.add(users);
            /*sendMesText(id,"/start buyrug'ini yuboring");*/
            users.setStep(BotSteps.HELP);

        }
        else if(message.equals(BotSteps.ADMIN)){
            //users.setStep(BotSteps.SELECT_LANG);
            botUsers.add(users);
            /*sendMesText(id,"/start buyrug'ini yuboring");*/
            users.setStep(BotSteps.ADMIN);

        }


        return users;
    }


    public void sendMesText(String chatId, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("xatolik");
        }
    }
    public void inlineButton(String chatId) {
        SendMessage sendMessage = new SendMessage();


        sendMessage.setText("Iltimos tilni tanlang\uD83D\uDC47");
        sendMessage.setChatId(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> td = new ArrayList<InlineKeyboardButton>();

        InlineKeyboardButton inlineKeyboardButtonUz = new InlineKeyboardButton();
        inlineKeyboardButtonUz.setText("\uD83C\uDDFA\uD83C\uDDFFUZ");
        inlineKeyboardButtonUz.setCallbackData(BotSteps.UZ);
        td.add(inlineKeyboardButtonUz);

        InlineKeyboardButton inlineKeyboardButtonEng = new InlineKeyboardButton();
        inlineKeyboardButtonEng.setText("\uD83C\uDDFA\uD83C\uDDF8ENG");
        inlineKeyboardButtonEng.setCallbackData(BotSteps.ENG);
        td.add(inlineKeyboardButtonEng);

        InlineKeyboardButton inlineKeyboardButtonRu = new InlineKeyboardButton();
        inlineKeyboardButtonRu.setText("\uD83C\uDDF7\uD83C\uDDFARU");
        inlineKeyboardButtonRu.setCallbackData(BotSteps.RU);
        td.add(inlineKeyboardButtonRu);



        List<List<InlineKeyboardButton>> tr = new ArrayList<List<InlineKeyboardButton>>();
        tr.add(td);


        inlineKeyboardMarkup.setKeyboard(tr);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            sticer(chatId);
        }
    }

    public void sticer(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Iltimos sticersiz xabar yuboring");
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /*public void sendMessege(String chatId, InlineKeyboardMarkup keyboard) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(keyboard);
        sendMessage.setChatId(chatId);
        execute(sendMessage);
    }*/


}