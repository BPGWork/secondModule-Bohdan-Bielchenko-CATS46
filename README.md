Цей проект придставляє Симуляцію Острова з різними типами тварин та рослинами

Острів має площу клітинок, які можна змінити в параметрах острова ( sumulationConfig.json )
Також там можна змінити яку кількість тваринок повинна містити клітинка на стадії проектування

Сутності також создаються через json файл ( entityParameters.json )
У цьому файлі ви можете змінити параметри сутності по типу (Вага, Скільки КГ їжі тваринка потребує, швидкість, максимальна кількість в клітинці)

Кожна тваринка може ходити, їсти відносно чи вона хижак або травоїдна, розмножатися
- Тваринка ходить в залежності від своєї скорості
- Хижак має вірогідність перемогти в схваткі або програти ( процент можна змінити в diet.json )
- Тваринки розмножуються тільки якщо їм є пара в клітинці
Також тваринка має здоров'я, якщо вона голодна - помирає
Усі дії виконуються паралельно, у проекті було задіяно багатопочність

Програма виводить на екран статистику острова та саму карту кожного тіку
Тік визначаєте ви самі. Якщо запускаєте jar файл у папці target:
- без аргумента тік становить 10
- 1 аргументом вкажіть число, воно буде дорівнювати тікам програми
Якщо запускаєте в IDE ( що я рекомендую через кодування емодзі які не підтримуються в консолі OC ):
- по замовчуванню 10
- добавете аргумент до масиву args - це ваша кількість тіків

#############################################################################################################################################################################################
Island Simulation Project

This project represents an Island Simulation with various types of animals and plants.
The island consists of a grid of cells, the size of which can be configured in the island parameters file (simulationConfig.json).
In the same configuration file, you can also define how many entities a single cell can contain at the design stage.

Entity Configuration
All entities are created using a JSON configuration file (entityParameters.json).
In this file, you can modify entity parameters such as:
- Weight
- Required amount of food (kg)
- Movement speed
- Maximum number of entities per cell

Animal Behavior
Each animal in the simulation can:
- Move
- Eat (depending on whether it is a predator or a herbivore)
- Reproduce

Behavior details:
- Animals move according to their speed
- Predators have a probability of winning or losing a fight when hunting (the probability can be configured in diet.json)
- Animals can reproduce only if a mating pair exists in the same c ell
-Each animal has health. If an animal becomes hungry, it dies

Concurrency
All actions in the simulation are executed in parallel.
The project actively uses multithreading to process island logic efficiently.

Output
The program displays:
- Island statistics
- The island map for each simulation tick

Running the Program
Running the JAR file (from the target directory)
Without arguments → the simulation runs for 10 ticks
With one argument → the argument value defines the number of ticks

Example:
java -jar island-simulation.jar 50

Running in IDE (recommended)
Running the program in an IDE is recommended because some emojis used in the output may not be supported by the OS console.
Default number of ticks: 10
To change it, add a value to the args array — this will define the number of simulation ticks


rewrite it in a more academic / course-project style

Just tell me 👍
