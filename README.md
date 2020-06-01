## The program performs A/B testing using 2 methods: 
1. Equals Probabilities
1. Mann-Whitney

Usage:
Build using command ```mvnw clean install``` run ```java -jar ./target/ab-test-spring-boot.jar``` then follow instructions.


### Задача 1.

Перед президентскими выборами  в городах Курске и Владивостоке был проведен социологический опрос. Каждый респондент должен был ответить на вопрос: «За какого кандидата вы будете голосовать на выборах?». В Курске опросили 105 человек, из них 42 ответили, что будут голосовать за кандидата А, во Владивостоке опросили 195 человек, из которых 65 за А. Можно ли считать на уровне значимости 0,05, что уровни поддержки кандидата А в Курске и Владивостоке одинаковы?

```textmate
$ java -jar ./target/ab-test-spring-boot.jar
EqualsProbabilityApproach[1] MannWhitneyApproach[2]: 1
Number A Success: 42
Number A Failures: 63
Number B Success: 65
Number B Failures: 130
Type of Alternative Hypothesis A<>B [1] A<B [2] A>B [3]: 1

T = 1.1497694901123454
Main Hypothesis is accepted? :true
P = 0.2502388245506464

=========-1.96----(T)1.15----1.96=========
```
_Ответ: Да, можно считать, что одинаковые._

### Задача 2.

Для изучения эффективности лекарства против аллергии обследовались две группы людей, предрасположенных к этому заболеванию. Результаты обследования следующие: среди принимавших лекарство заболело 3 человека, не заболело 172 человека; среди не  принимавших заболело 32 человека, не заболело 168. Указывают ли эти результаты на эффективность лекарства?

```textmate
$ java -jar ./target/ab-test-spring-boot.jar
EqualsProbabilityApproach[1] MannWhitneyApproach[2]: 1
Number A Success: 172
Number A Failures: 3
Number B Success: 168
Number B Failures: 32
Type of Alternative Hypothesis A<>B [1] A<B [2] A>B [3]: 3

T = 4.744365458250511
Main Hypothesis is accepted? :false
P = 1.0458046975081459E-6

---------1.64====(T)4.74====
```
_Ответ: Да, можно считать, что лекарство эффективное._

### Задача 3
Было проведено обследование 10 горожан и 9 жителей села примерно одного возраста. Получены следующие данные об уровне давления:

для горожан: 130, 110, 120, 140, 200, 130, 140, 170, 160, 140;

для селян: 120, 190, 130, 160, 150, 120, 110, 120, 200.

Свидетельствуют ли эти данные в пользу того, что горожане имеют в среднем более высокое давление чем жители сельской местности?

```textmate
$ java -jar ./target/ab-test-spring-boot.jar
EqualsProbabilityApproach[1] MannWhitneyApproach[2]: 2
Enter selection A: 130,110,120,140,200,130,140,170,160,140
Enter selection B: 120,190,130,160,150,120,110,120,200
Type of Alternative Hypothesis Teta>0 [1] Teta<0 [2] Teta<>0 [3]: 1

T = 0.32659863237109044
Main Hypothesis is accepted? :true
P = 0.3719857390375285

----(T)0.33----1.64=========
Distribution:
+o+ooo++o+++o+o+o+o

```
_Ответ: Нет, не свидетельствуют._

### Задача 4
Уровень гистамина в мокроте у 7 курильщиков, склонных к аллергии, составил в мг:

102.4,100.0,67.6,65.9,64.7,39.6,31.2

У 10 курильщиков не склонных к аллергии составил в мг:

48.1,45.5,41.7,35.4,29.1,18.9,58.3,68.8,71.3,94.3

Можно ли, основываясь на этих данных, считать с надёжностью 0,95 что уровень гистамина у склонных и не склонных к аллергии курильщиков значимо различается?
```textmate
$ java -jar ./target/ab-test-spring-boot.jar
EqualsProbabilityApproach[1] MannWhitneyApproach[2]: 2
Enter selection A: 102.4,100.0,67.6,65.9,64.7,39.6,31.2
Enter selection B: 48.1,45.5,41.7,35.4,29.1,18.9,58.3,68.8,71.3,94.3
Type of Alternative Hypothesis Teta>0 [1] Teta<0 [2] Teta<>0 [3]: 3

T = 1.0734900802433864
Main Hypothesis is accepted? :true
P = 0.28305128705188487

=========-1.96----(T)1.07----1.96=========
Distribution:
oo+o+oooo+++ooo++

```
_Ответ: Нет, нельзя считать что уровень значимо различается._
