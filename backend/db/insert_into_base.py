from utils.group_to_base import group_to_base
from utils.psql import db, session
from create_tables import Questions

questions = group_to_base()

for i, r in questions.iterrows():
    print(r['wrong_answer'])
    questionss = Questions(
        question=r['question'],
        wrong_answers=r['wrong_answer'],
        right_answer=r['right_answer'],
        quest_name=r['quest_name'],
        coins=r['coins'])
    session.add(questionss)
    session.commit()
