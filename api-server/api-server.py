from flask import Flask, request
from json import dumps

app = Flask(__name__)

@app.route("/evaluate")
def evaluate():
	error_flag = False
	expression = request.args["expression"]
	expression = "".join(list(map(lambda x: x if x != "p" else "+", list(expression))))
	try:
		expression_result = eval(expression)
	except Exception as e:
		print(e)
		error_flag = True
		expression_result = "Evaluation error"
	result = {
		"status": "success" if not error_flag else "error",
		"content": expression_result
	}
	return dumps(result)



if __name__ == "__main__":
	import os
	os.startfile("translator.exe")
	# domain - https://kaelesty-api-calc.loca.lt
	app.run(port=8000)