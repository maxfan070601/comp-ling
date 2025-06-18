from tkinter import *
from tkinter import ttk
import re
import random



def response_to(question:str)->str:
    question = question.lower()
    if question.startswith("hello"):
        return "Why, hello there."
    elif "groton" in question:
        if question.startswith("why") or question.startswith("how"):
            return "Because Groton is the best school of all time."
        elif "should" in question or "would" in question:
            return "Yes, 100%."
        else:
            return "Schedule an interview with Mr. Gnozzio to learn more."
    elif "st. mark's" in question:
        return "They suck."
    elif question == "where are the cookies?":
        return "In the cookie jar!"
    elif question.startswith("where"):
        return "To the North!"
    elif question.startswith("why"):
        return "Why not?"
    elif question.startswith("how"):
        return "The power of Jesus Christ and the Holy Spirit."
    elif "in" in question:
        return "There is a time and place for everything."
    else:
        default_number = len(question) % 3
        if default_number == 0:
            return "That really depends."
        elif default_number == 1:
            return "Perchance."
        else:
            return "Who knows?"
    

root = Tk()
frm = ttk.Frame(root, padding=20)
frm.grid()
ttk.Label(frm, text=u"\U0001F976", font=("Ariel", 36)).grid(column=0, row=0)
a = ttk.Label(frm, text="Welcome to Question Bot. Please ask a question.")
a.grid(column=1, row=0)

ttk.Label(frm, text=' ', font=("Ariel", 4)).grid(column=0, row=1)

q = Text(frm, height=5, width=55)
q.grid(column=0, row=2, columnspan = 2)

def answer_question():
    question = q.get("1.0", END)
    a.config(text=response_to(question.strip()))
    q.delete("1.0",END)

buttons = ttk.Frame(frm, padding=10)
ttk.Button(buttons, text="Ask", command=answer_question, width=18).grid(column=0, row=0)
ttk.Button(buttons, text="Quit", command=root.destroy, width=18).grid(column=1, row=0)
buttons.grid(row=3, column=0, columnspan=2)
root.mainloop()