from flask import Flask

app = Flask(__name__)

@app.route("/evaluate/<expression>")
def evaluate(expression):
	try:
		return str(eval(expression))
	except SyntaxError:
		return "Invalid expression"


if __name__ == "__main__":
	import os
	os.startfile("translator.exe")
	# domain - https://kaelesty-api-calc.loca.lt
	app.run(port=8000)