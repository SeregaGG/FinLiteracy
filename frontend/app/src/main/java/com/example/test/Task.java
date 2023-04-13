package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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
        location = arguments.get("location").toString();

        setContentView(R.layout.activity_task_choosing);
    }

    public void easyTask(View view) {
        setContentView(R.layout.activity_task);
        TextView textView = findViewById(R.id.taskDescription);
        textView.setText(location);
    }

    public void middleTask(View view) {
        setContentView(R.layout.activity_task);
        TextView textView = findViewById(R.id.taskDescription);
        textView.setText(location);
    }

    public void hardTask(View view) {
        setContentView(R.layout.activity_task);
        TextView textView = findViewById(R.id.taskDescription);
        textView.setText(location);
    }

    public static Question getQuestion(int coins, String location) {
        return questions_.stream().filter(question -> question.coins == coins).findFirst().get();
    }

    public static void putQuestions(List<Question> list) {
        questions_ = list;
    }

    String a = "[{\"question\":\"Однажды Жанна стала свидетелем, как некий человек, пытался установить на банкомат какое-то оборудование. Жанна решила позвонить в службу безопасности банка и предупредить их, но она забыла, как называется этот вид мошенничества.\nСможете помочь Жанне вспомнить?\",\"wrong_answers\":[\"\tФишинг\"],\"right_answer\":\"Скимминг\",\"quest_name\":\"Банкомат\",\"coins\":400,\"location\":\"bank\"},{\"question\":\" Андрей вечером обнаружил, что потерял банковскую карту. \nСегодня он расплачивался картой в трех магазинах. \nЧто нужно сделать Андрею? \",\"wrong_answers\":[\"Ничего страшного. Это не первый раз. А у карты есть PIN-код. Нужно завтра зайти в те же магазины и просто забрать ее. Нужно только не забыть взять паспорт. \",\"Срочно заявить в полицию, чтобы они нашли и вернули банковскую карту. \"],\"right_answer\":\"Заблокировать карту и обратиться в банк с просьбой о выдаче новой карты. \",\"quest_name\":\"Магазин\",\"coins\":200,\"location\":\"shop\"},{\"question\":\"На улице Андрею дали листовку, где было написано, что финансово-кредитная компания «Единственный шанс» принимает сбережения граждан и открывает депозиты под 70 % годовых в рублях. Андрей хочет воспользоваться этим предложением.\nЧто нужно сделать Андрею, чтобы проверить надежность компании?\nВыберите все верные ответы.\",\"wrong_answers\":[\"Для проверки нужно положить в эту компанию не всю, а только 1/3 суммы\",\"Расспросить человека, раздающего листовки.\"],\"right_answer\":\"Проверить наличие лицензии у компании, Ознакомиться с договором, который должен быть заключён между компанией и вкладчиком о гарантированной прибыли\",\"quest_name\":\"Финансовая организация\",\"coins\":300,\"location\":\"fin_org\"},{\"question\":\"Вопрос: На электронную почту Вам пришло сообщение с предложением принять участие в глобальной Интернет-игре. Это очень интересный квест. Победители получат денежный приз и годовой абонемент в один из лучших фитнес-центров Вашего города. Вступительный взнос всего 300 рублей. \nВыберите правильный вариант действий: \",\"wrong_answers\":[\"Перечислить деньги. Годовой абонемент в фитнес-центр очень привлекательный приз. К тому же и в квесте хочется принять участие. \",\"Конечно, приму участие. 300 рублей сумма небольшая. Можно рискнуть. Даже если это мошенники, получу опыт\"],\"right_answer\":\"Отказаться. Это явно мошенники, цель которых собрать деньги. Скорее всего, этот «квест» обычная пирамида. \",\"quest_name\":\"Развлекательный центр\",\"coins\":200,\"location\":\"entertainment_center\"},{\"question\":\"В холле учебного центра Вы нашли безымянную банковскую карту.\nВыберите правильный вариант действий:\",\"wrong_answers\":[\"\tОтлично. Повезло. Карта безымянная, значит, и владельца у нее нет. Можно купить себе чего-нибудь.\",\"\tОставить на месте находки объявление со своими координатами. Владелец найдется. Можно рассчитывать на вознаграждение.\"],\"right_answer\":\"\tОбратиться в банк по указанным в карте реквизитам и сообщить о находке.\",\"quest_name\":\"Учебный центр\",\"coins\":200,\"location\":\"school\"}]";
    private static List<Question> questions_ = new ArrayList<>();
    String location = "";
    int coind = 0;
}