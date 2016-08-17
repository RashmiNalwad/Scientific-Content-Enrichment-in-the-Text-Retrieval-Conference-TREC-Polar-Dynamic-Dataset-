<?php

$username = 'admin';
$password = 'admin';

$url = $_GET['FILE'];
$title=$_GET['TITLE'];
echo $url;
echo $title;
$format  = 'json';                       // output format: 'json', 'xml' or 'simple'
$api_url = 'http://localhost/YOURLS/yourls-api.php';
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $api_url);
curl_setopt($ch, CURLOPT_HEADER, 0);            // No header in the result
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Return, do not echo result
curl_setopt($ch, CURLOPT_POST, 1);              // This is a POST request
curl_setopt($ch, CURLOPT_POSTFIELDS, array(      // Data to POST
		'url'      => $url,
		'format'   => $format,
		'action'   => 'shorturl',
        'title'    => $title,
		'username' => $username,
		'password' => $password
	));
$data = curl_exec($ch);
curl_close($ch);
echo $data;
?>



