<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>心情表态</title>
<link href="a_data/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table align="center" border="0" cellpadding="3" cellspacing="1" width="100%">
<form action="counter" method="post" name="moodform" id="moodform">
	<input name="articleId" id="articleId" value="${article.id}" type="hidden">
  <tbody><tr valign="bottom">
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody>
            <tr>
				<td height="100" align="center" valign="bottom">
                    <span id="good_times_span">${article.good_times?default("0")}</span><br>
					<img id="good_times_img" src="../resources/img/bg2.gif" height="${article.good_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood1.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">精彩</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="good_times" type="radio">
				</td>
            </tr>
          </tbody>
          </table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
                    <span id="touch_times_span">${article.touch_times?default("0")}</span><br>
					<img id="touch_times_img" src="../resources/img/bg2.gif" height="${article.touch_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood2.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">
					感动				</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="touch_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
                    <span id="funny_times_span">${article.funny_times?default("0")}</span><br>
					<img id="funny_times_img" src="../resources/img/bg1.gif" height="${article.funny_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood3.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">
					搞笑				</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="funny_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
                    <span id="happy_times_span">${article.happy_times?default("0")}</span><br>
					<img id="happy_times_img" src="../resources/img/bg2.gif" height="${article.happy_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood4.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">  
					开心				</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="happy_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
					<span id="anger_times_span">${article.anger_times?default("0")}</span><br>
					<img id="anger_times_img" src="../resources/img/bg1.gif" height="${article.anger_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood5.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">
					愤怒				</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="anger_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
					<span id="bored_times_span">${article.bored_times?default("0")}</span><br>
					<img id="bored_times_img" src="../resources/img/bg1.gif" height="${article.bored_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood6.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">
					无聊				</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="bored_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
					<span id="water_times_span">${article.water_times?default("0")}</span><br>
					<img id="water_times_img" src="../resources/img/bg1.gif" height="${article.water_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center" > 
					<img src="../resources/img/mood7.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">灌水</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="water_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
      <td align="center">
          <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody><tr>
				<td height="100" align="center" valign="bottom">
					<span id="surprise_times_span">${article.surprise_times?default("0")}</span><br>
					<img id="surprise_times_img" src="../resources/img/bg1.gif" height="${article.surprise_times_height?default("0")}" border="0" width="20">
				</td>
            </tr>
            <tr>
				<td height="42" align="center"> 
					<img src="../resources/img/mood8.gif" height="32" border="0" width="32">
				</td>
            </tr>
            <tr> 
				<td height="20" align="center" valign="top">
					惊讶</td>
            </tr>
            <tr> 
				<td align="center"> 
					<input name="column" value="surprise_times" type="radio">
				</td>
            </tr>
          </tbody></table>
    </td>
    </tr>
</tbody>
</form>
</table>

</body></html>