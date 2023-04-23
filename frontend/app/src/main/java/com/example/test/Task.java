package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class Task extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Bundle arguments = getIntent().getExtras();
        location_ = arguments.get("location").toString();

        setContentView(R.layout.activity_task_choosing);
        QuestManager.updateCoins(this);
    }

    // activity_task_choosing
    public void easyTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = getQuestion(kEasyTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kEasyTaskReward;
    }

    // activity_task_choosing
    public void middleTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = getQuestion(kMiddleTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kMiddleTaskReward;
    }

    // activity_task_choosing
    public void hardTask(View view) {
        setContentView(R.layout.activity_task);
        QuestManager.removeCoins(kQuestionPrice);
        QuestManager.updateCoins(this);
        question_ = getQuestion(kHardTaskReward, location_);

        TextView textView = findViewById(R.id.taskDescription);
        TextView a = findViewById(R.id.txtA);
        TextView b = findViewById(R.id.txtB);
        TextView c = findViewById(R.id.txtC);
        textView.setText(question_.question);
        a.setText(question_.right_answer);
        b.setText(question_.wrong_answers[0]);
        if (question_.wrong_answers.length > 1) {
            c.setText(question_.wrong_answers[1]);
        }

        buffer_ = kHardTaskReward;
    }

    public void returnToMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    // activity_task
    public void onClickFirst(View view) {
        TextView a = findViewById(R.id.txtA);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickSecond(View view) {
        TextView a = findViewById(R.id.txtB);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    // activity_task
    public void onClickThird(View view) {
        TextView a = findViewById(R.id.txtC);
        answer_ = (String) a.getText();
        view.setBackgroundResource(R.drawable.rectangle_bg_gray_300);
    }

    public void onClickAnswer(View view) {
        if (answer_.equals(question_.right_answer)) {
            QuestManager.addCoins(buffer_);
            QuestManager.updateCoins(this);
            buffer_ = 0;
            updateLocationStatuses(true);
            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
        } else {
            buffer_ = 0;
            updateLocationStatuses(false);
            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
        }
    }

    private void updateLocationStatuses(boolean is_passed) {
        var result = is_passed ? QuestManager.locationStatuses.CORRECT_ANSWER : QuestManager.locationStatuses.WRONG_ANSWER;
        if (location_.equals("school")) {
            QuestManager.school_status = result;
        } else if (location_.equals("shop")) {
            QuestManager.shop_status = result;
        } else if (location_.equals("mall")) {
            QuestManager.mall_status = result;
        } else if (location_.equals("bank")) {
            QuestManager.bank_status = result;
        } else if (location_.equals("fin_org")) {
            QuestManager.fin_org_status = result;
        } else {
            throw new IllegalArgumentException("bad location");
        }
    }

    public static Question getQuestion(int coins, String location) {
        return questions_.stream().filter(question -> question.coins == coins).findFirst().get();
    }

    public static void putQuestions(List<Question> list) {
        questions_ = list;
    }

    static ObjectMapper objectMapper = new ObjectMapper();
    static String a = "[{\"question\":\"Однажды Жанна стала свидетелем, как некий человек, пытался установить на банкомат какое-то оборудование. Жанна решила позвонить в службу безопасности банка и предупредить их, но она забыла, как называется этот вид мошенничества. Сможете помочь Жанне вспомнить?\",\"wrong_answers\":[\"tФишинг\"],\"right_answer\":\"Скимминг\",\"quest_name\":\"Банкомат\",\"coins\":400,\"location\":\"bank\"},{\"question\":\" Андрей вечером обнаружил, что потерял банковскую карту.  Сегодня он расплачивался картой в трех магазинах.  Что нужно сделать Андрею? \",\"wrong_answers\":[\"Ничего страшного. Это не первый раз. А у карты есть PIN-код. Нужно завтра зайти в те же магазины и просто забрать ее. Нужно только не забыть взять паспорт. \",\"Срочно заявить в полицию, чтобы они нашли и вернули банковскую карту. \"],\"right_answer\":\"Заблокировать карту и обратиться в банк с просьбой о выдаче новой карты. \",\"quest_name\":\"Магазин\",\"coins\":200,\"location\":\"shop\"},{\"question\":\"На улице Андрею дали листовку, где было написано, что финансово-кредитная компания «Единственный шанс» принимает сбережения граждан и открывает депозиты под 70 % годовых в рублях. Андрей хочет воспользоваться этим предложением. Что нужно сделать Андрею, чтобы проверить надежность компании? Выберите все верные ответы.\",\"wrong_answers\":[\"Для проверки нужно положить в эту компанию не всю, а только 1/3 суммы\",\"Расспросить человека, раздающего листовки.\"],\"right_answer\":\"Проверить наличие лицензии у компании, Ознакомиться с договором, который должен быть заключён между компанией и вкладчиком о гарантированной прибыли\",\"quest_name\":\"Финансовая организация\",\"coins\":300,\"location\":\"fin_org\"},{\"question\":\"Вопрос: На электронную почту Вам пришло сообщение с предложением принять участие в глобальной Интернет-игре. Это очень интересный квест. Победители получат денежный приз и годовой абонемент в один из лучших фитнес-центров Вашего города. Вступительный взнос всего 300 рублей.  Выберите правильный вариант действий: \",\"wrong_answers\":[\"Перечислить деньги. Годовой абонемент в фитнес-центр очень привлекательный приз. К тому же и в квесте хочется принять участие. \",\"Конечно, приму участие. 300 рублей сумма небольшая. Можно рискнуть. Даже если это мошенники, получу опыт\"],\"right_answer\":\"Отказаться. Это явно мошенники, цель которых собрать деньги. Скорее всего, этот «квест» обычная пирамида. \",\"quest_name\":\"Развлекательный центр\",\"coins\":200,\"location\":\"entertainment_center\"},{\"question\":\"В холле учебного центра Вы нашли безымянную банковскую карту. Выберите правильный вариант действий:\",\"wrong_answers\":[\"tОтлично. Повезло. Карта безымянная, значит, и владельца у нее нет. Можно купить себе чего-нибудь.\",\"tОставить на месте находки объявление со своими координатами. Владелец найдется. Можно рассчитывать на вознаграждение.\"],\"right_answer\":\"tОбратиться в банк по указанным в карте реквизитам и сообщить о находке.\",\"quest_name\":\"Учебный центр\",\"coins\":200,\"location\":\"school\"}]";
    private static List<Question> questions_;

    static {
        try {
            questions_ = Arrays.asList(objectMapper.readValue(a, Question[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int kEasyTaskReward = 200;
    private static final int kMiddleTaskReward = 300;
    private static final int kHardTaskReward = 400;
    private static final int kQuestionPrice = 100;
    Question question_;
    String location_ = "";
    String answer_ = "";
    int buffer_ = 0;
}