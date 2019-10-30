<?php 
    //getting the database connection
	require "conn.php";
	use PHPMailer\PHPMailer\PHPMailer;
	use PHPMailer\PHPMailer\Exception;
	//an array to display response
	$response = array();
	
	function randomPassword() {
		$alphabet = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
		$pass = array(); //remember to declare $pass as an array
		$alphaLength = strlen($alphabet) - 1; //put the length -1 in cache
		for ($i = 0; $i < 12; $i++) {
			$n = rand(0, $alphaLength);
			$pass[] = $alphabet[$n];
		}
		return implode($pass); //turn the array into a string
	}
	//if it is an api call 
	//that means a get parameter named api call is set in the URL 
	//and with this parameter we are concluding that it is an api call 
	if(isset($_GET['apicall'])){
		switch($_GET['apicall']){
			case 'login':
				//for login we need the username and password
				if(isTheseParametersAvailable(array('email', 'password'))){
					//getting values
					$email = $_POST['email'];
					$password = ($_POST['password']);

					//creating the query
					
					$stmt = $conn->prepare("SELECT id, name, batch, regno FROM users WHERE email = ? and password = ?");
					$stmt->bind_param("ss",$email, $password);
					$stmt->execute();
					$stmt->store_result();

					//if the user exist with given credentials

					if($stmt->num_rows > 0){

						$stmt->bind_result($id, $name, $batch, $regno);
						$stmt->fetch();

						$user = array(
							'id'=>$id,
							'name'=>$name,
							'batch'=>$batch,
							'regno'=>$regno,
						);

						$response['error'] = false;
						$response['message'] = 'Login successfull';
						$response['user'] = $user;
					}else{
						//if the user not found
						$response['error'] = true;
						$response['message'] = 'Invalid username or password';
					}
				}
			break;

			case 'grades':
				if(isTheseParametersAvailable(array('regno'))){
					//getting values
					$regno = $_POST["regno"];

					//creating the query
					$stmt = $conn->prepare("SELECT Roll, MA401, CS401, CS402, EC401, EC402, CS411, CS412, CS413 from students WHERE Reg_id= ?");
					$stmt->bind_param("s",$regno);

					$stmt->execute();

					$stmt->store_result();

					if($stmt->num_rows > 0){

						$stmt->bind_result($Roll, $MA401, $CS401, $CS402, $EC401, $EC402, $CS411, $CS412, $CS413);
						$stmt->fetch();
						$student_grades = array(
							'Roll'=>strval($Roll),
							'MA401'=>$MA401,
							'CS401'=>$CS401,
							'CS402'=>$CS402,
							'EC401'=>$EC401,
							'EC402'=>$EC402,
							'CS411'=>$CS411,
							'CS412'=>$CS412,
							'CS413'=>$CS413,
						);

						$response['error'] = false;
						$response['message'] = 'Grades fetched successfull';
						$response['student_grades'] = $student_grades;
					}else{
						//if the user not found
						$response['error'] = false;
						$response['message'] = 'Invalid Registraton No.';
					}
				}
			break;
			default: 
				$response['error'] = true; 
				$respnse['message'] = 'Invalid Operation Called';
		}
		
	}else{
		//if it is not api call 
		//pushing appropriate values to response array 
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	//displaying the response in json structure 
	echo json_encode($response);
	
	
	     //function validating all the paramters are available
	//we will pass the required parameters to this function 
	function isTheseParametersAvailable($params){
		
		//traversing through all the parameters 
		foreach($params as $param){
			//if the paramter is not available
			if(!isset($_POST[$param])){
				//return false 
				return false; 
			}
		}
		//return true if every param is available 
		return true; 
	}
?>