# Tangibles: To-Do List and Tasks

### *Elevate your productivity!*

Keeping up with day-to-day tasks can be extremely stressful and overwhelming. Effective to-do lists can help reduce 
anxiety, provide structure, and keep record of accomplishments. That's why Tangibles was created. Tangibles is a simple, 
to-do list application meant for people who aspire to be more organized, motivated, and productive when it comes to 
completing tasks. Tangibles offers several features that allow users to:

- Add and delete tasks
- Assign an urgency level to a task
- Add additional notes to a task

Tangibles is inspired by Alex Ikonn and UJ Ramdas' renowned [Productivity Planner](https://www.intelligentchange.com/products/the-productivity-planner)
, a physical planner that aims to solve the "time versus productivity conundrum." First, it asks users to rank their 
tasks from most to least important. Why? The most important task is usually the most uncomfortable or most 
procrastinated upon. Then, it encourages users to start with the most important task! By starting with the most 
important task, users may feel a great sense of relief and accomplishment when they complete it. I know I do. I love my 
Productivity Planner, and was super inspired to create Tangibles, a Java desktop application that offers similar 
(and more) features!

## User Stories

- As a user, I want to be able to add a task to my to-do list.
- As a user, I want to be able to delete a task from my to-do-list.
- As a user, I want to be able to assign an urgency level to a task.
- As a user, I want to be able to add additional notes to a task.
- As a user, I want to be able to print all tasks in my to-do list.
- As a user, I want to be able to save my to-do list to file.
- As a user, I want to be able to load my to-do list from file.

### Phase 4: Task 2
A Task has an urgency level, title, and additional notes. Additional notes are optional, and urgency level is set to
EXTREMELY by default. However, a task is required to have a title; it cannot be made without one. Therefore, the Task 
constructor throws a checked exception, EmptyTitleException. (Please see the Task and TaskTest classes.)

### Phase 4: Task 3
Currently, ToDoListGUI has quite a few fields and methods related to buttons and text fields. I believe the following
meaning-preserving changes would help improve the structure of ToDoListGUI:
- Add a Buttons package in the ui package. In the Buttons package, I would add an abstract Button class, and a class for 
  each button that extends the abstract Button class.
- Add a TextField package in the ui package. In the TextField package, I would add an abstract TextField class, and a 
  class for each text field that extends the abtract TextField class.
    

