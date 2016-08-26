<!DOCTYPE html>
<html>
<head>
    <#include "../common/meta.ftl">
    <#include "../common/head.ftl">
    <title>插入代码</title>
    <style>
        body { overflow: hidden;}
    </style>
    <script type="application/javascript">
        $(document).ready(function(){
            $("#insert_code_button").click(function(){
                var content = '<pre class="brush: ' + $("#language_select").val() + '; ';
                content = content + 'collapse :' + $("#is_collapse_input").is(":checked") + ';';
                content = content + 'gutter :true;">';
                content = content + $("#code_textarea").val() + '</pre><br/>';
                top.tinymce.activeEditor.insertContent(content);
                top.tinymce.activeEditor.windowManager.close();
            });
        });
    </script>
</head>
<body>
    <div>
        <table class="form_table"  style="width:780px;height:380px;border: hidden;margin: 10px;">
            <tr>
                <td class="form_info">
                    编程语言:
                </td>
                <td class="form_input">
                    <select name="language" id="language_select">
                        <option value="applescript">AppleScript</option>
                        <option value="as3">AS3</option>
                        <option value="bash">Bash</option>
                        <option value="coldfusion">ColdFusion</option>
                        <option value="cpp">C++</option>
                        <option value="csharp">C#</option>
                        <option value="css">Css</option>
                        <option value="delphi">Delphi</option>
                        <option value="diff">Diff</option>
                        <option value="erlang">Erlang</option>
                        <option value="groovy">Groovy</option>
                        <option value="java" selected="selected">Java</option>
                        <option value="javafx">JavaFX</option>
                        <option value="js">JScript</option>
                        <option value="perl">Perl</option>
                        <option value="php">Php</option>
                        <option value="plain">Plain</option>
                        <option value="powershell">PowerShell</option>
                        <option value="python">Python</option>
                        <option value="ruby">Ruby</option>
                        <option value="sass">Sass</option>
                        <option value="scala">Scala</option>
                        <option value="sql">Sql</option>
                        <option value="vb">Vb</option>
                        <option value="xml">Xml</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="form_info">
                    选项:
                </td>
                <td class="form_input">
                    <span style="font-size: 12px;line-height: 25px;height: 25px;margin: 3px 0px;">
                        <input id="is_collapse_input" type="checkbox" name="isCollapse"/>
                        <label for="is_collapse_input">是否折叠</label>
                    </span>
                </td>
            </tr>
            <tr>
                <td valign="top"  class="form_info">
                    代码:
                </td>
                <td class="form_input">
                    <textarea name="code" id="code_textarea" style="width:600px;height:200px;"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td>
                    <input class="form_button" type="button" value="确定" id="insert_code_button" />&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
