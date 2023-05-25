from flask import Flask

app = Flask(__name__)

@app.route("/evaluate/<expression>")
def evaluate(expression):
	try:
		return str(eval(expression))
	except SyntaxError:
		return "Invalid expression"


if __name__ == "__main__":
	app.run(port=8000)