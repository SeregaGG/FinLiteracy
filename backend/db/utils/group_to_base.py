import pandas as pd


def group_to_base():
    df = pd.read_excel('Вопросы, в формате для базы.xlsx')
    df_grouped = df.groupby(['question', 'right_answer', 'quest_name', 'coins'], as_index=False) \
        .agg({'wrong_answer': tuple})
    return df_grouped
