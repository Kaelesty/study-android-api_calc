from flask import Flask

app = Flask(__name__)

@app.route("/evaluate/<expression>")
def evaluate(expression):
	return str(eval(expression))


if __name__ == "__main__":
	app.run(port=8000)