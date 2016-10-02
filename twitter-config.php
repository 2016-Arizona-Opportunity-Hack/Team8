<?php
/**
@author muni
@copyright http:www.smarttutorials.net
 */

require_once 'messages.php';

//site specific configuration declartion
define( 'BASE_PATH', 'http://demo.smarttutorials.net/twitter/');
define( 'DB_HOST', 'localhost' );
define( 'DB_USERNAME', 'root');
define( 'DB_PASSWORD', '');
define( 'DB_NAME', 'user_login');

//Twitter login
define('TWITTER_CONSUMER_KEY', 'JqXTytyqpfEd72RiqzgCCoXVK');
define('TWITTER_CONSUMER_SECRET', 'f4RQNkB3h0YofvgCsiT6Fs8cEesdxyFQ7oi1WHYo3JmEh6leAf');
define('TWITTER_OAUTH_CALLBACK', 'https://opportunity-hack-2016-az.github.io/Team8/profile.html');

function __autoload($class)
{
	$parts = explode('_', $class);
	$path = implode(DIRECTORY_SEPARATOR,$parts);
	require_once $path . '.php';
}