from tkinter import *
from tkinter import ttk
import re
import random

import re
import random

grammar = {
    "GREETING": ["Hello!", "Hi there!", "What's up?", "What's poppin?", "G'day, mate!", "Howdy, partner!"],
    "ADVICE": ["Do you need advice?", "How can I assist you?", "What would you like to know?"],
    "AFFIRMATION": ["Yes, of course.", "Certainly.", "Absolutely.", "For sure.", "No doubt about it."],
    "NEGATION": ["No way!", "No, not really.", "I don't think so.", "Probably not.", "Definitely not."],
    "QUESTION_RESPONSE": [
        "That's an interesting question.",
        "I'm not sure.",
        "Can you clarify?",
        "Let's think about it."
    ],
    "LOVE_RESPONSE": [
        "Everyone loves {person}.",
        "No one loves {person}.",
        "Love is complicated, isn't it?"
    ],
    "FUTURE_ADVICE": [
        "You should {action}.",
        "Maybe {action} would help.",
        "How about trying to {action}?"
    ]
}

def generate_response(word, person=None, action=None):
    if word not in grammar:
        return word
    
    production = random.choice(grammar[word]) 
    response_parts = []


    for part in production.split():
        if part in grammar:
            response = generate_response(part, person, action)
        else:
            response = part
            if person:
                response = response.format(person=person)
            if action:
                response = response.format(action=action) 
        response_parts.append(response)
    
    return " ".join(response_parts)

regexes = {
    "greeting": re.compile(r"hello|hi|hey", re.IGNORECASE),
    "love": re.compile(r"does\s(.+?)\s(love|like)\s(me|.+)", re.IGNORECASE),
    "future": re.compile(r"(what|how)\s(should|could|can)\s(i|we|.+?)\s(.+?)", re.IGNORECASE),
}

def response_to(question: str) -> str:
    question = question.strip().lower() 

    if regexes["greeting"].search(question):
        return generate_response("GREETING") + " " + generate_response("ADVICE")

    match = regexes["love"].match(question)
    if match:
        person = match.group(1).strip() if match.group(1) != "me" else "you"
        if match.group(3) == "me":
            person = "you" 
        return generate_response("LOVE_RESPONSE", person=person)
    
    match = regexes["future"].match(question)
    if match:
        action = match.group(4).strip()
        return generate_response("FUTURE_ADVICE", action=action)
    
    return generate_response("QUESTION_RESPONSE")

 
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
