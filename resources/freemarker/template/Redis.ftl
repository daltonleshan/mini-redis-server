<#assign content>

<div class="container">

<header>
   <h1>Mini-Redis-Server</h1>
</header>
  
<nav>
<form name = "myForm" action="">
  <div id = "radios">
  	<p>Values:</p>
  		<input type="radio" name = "radio" id="string" value="string" checked> String<br>
  		<input type="radio" name = "radio" id="list" value="list"> List<String><br>
  		<input type="radio" name = "radio" id="map" value="map"> Map<String, String>
  </div>
  
  <p>Operation:</p>
	<select id = "commands">
		<option value="get">GET</option>
		<option value="set">SET</option>
		<option value="delete">DELETE</option>
		<option value="search">SEARCH-KEYS</option>
		<option value="all">GET-KEYS</option>
	</select>
</form>
  
</nav>
<div id = "querybox">
	<p>	Query:</p>
	<input type = "text" id = "query" autocomplete="on" placeholder = "key, value">*
	<font id = "error" size="2" color = "red">This is a required field!</font>
	<p> <button onclick="sendEnd()" >Submit</button> </p>
	<ul id = "display"> </ul>
</div>

<footer>&copy; dthuku: fullstack engineering interview #tradeghana</footer>



</div>

</#assign>
<#include "main.ftl">