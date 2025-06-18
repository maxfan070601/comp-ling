from tkinter import *
from tkinter import ttk
import re
import random



def response_to(question:str)->str:
    question = question.lower()
    re1 = re.compile("(is|are|was)\s((the|a|some|your|my)?\s?.*?)\s(.*)")
    re2 = re.compile("does\s(.+?)\s(love|like)\s(me|.*)")
    re3 = re.compile("who\s(loves|likes)\s(me|you|.*)")
    re4 = re.compile("what\s(can|could|shall|should)\s(i.*?|.+?)\s(.*?)\s(with?)\s(.+)")
    if question.startswith("hello"):
        return "Why, hello there. Do you need advice?"
    elif re1.match(question):
        match = re1.match(question).groups()
        if random.choice([0,1]) == 0:
            return match[1] + " " + match[0] + " not "  + match[3]
        else:
            return match[1] + " " + match[0] + " " + match[3]
    elif re2.match(question):
        match = re2.match(question).groups()
        c = random.choice([0,1])
        if c == 0:
            if match[2] == "me":
                return match[0] + " " + match[1] + "s you a lot."
            else:
                return match[0] + " " + match[1] + "s " + match[2] + " a lot."
        else:
            if match[2] == "me":
                return match[0] + " does not " + match[1] + " you."
            else:
                return match[0] + " does not " + match[1] + " " + match[2] + "."
    elif re3.match(question):
        match = re3.match(question).groups()
        c = random.choice([0,1,2])
        if c == 0:
            if match[1] == "me":
                return "Everyone " + match[0] + " you."
            elif match[1] == "you":
                return "Everyone " + match[0] + " me."
            else:
                return "Everyone " + match[0] + " " + match[1] + "."
        elif c == 1:
            if match[1] == "me":
                return "No one " + match[0] + " you."
            elif match[1] == "you":
                return "No one " + match[0] + " me."
            else:
                return "No one " + match[0] + " " + match[1] + "."
        else:
            return "Outlook unclear. Try again later. But keep up the good work."
    elif re4.match(question):
        match = re4.match(question).groups()
        if match[1] == "i":
            return "You " + match[0] + " " + match[2] + " what you initially planned to " + match[2] + "."
        return None
    else:
        return "Ask again later. We are still under development."
    
 
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