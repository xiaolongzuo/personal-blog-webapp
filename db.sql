DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS visitors;

CREATE TABLE users
(
  username CHAR(15) NOT NULL CHECK(username !=''),
  password CHAR(15) NOT NULL,
  email VARCHAR(20) NOT NULL UNIQUE,
  nickname VARCHAR(20) NOT NULL UNIQUE,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(username)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE articles
(
  id INT NOT NULL AUTO_INCREMENT,
  username CHAR(15) NOT NULL CHECK(username !=''),
  subject VARCHAR (40) NOT NULL,
  content TEXT NOT NULL,
  icon VARCHAR (200) NOT NULL,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  access_times INT DEFAULT 0,
  comment_times INT DEFAULT 0,
  good_times INT DEFAULT 0,
  touch_times INT DEFAULT 0,
  funny_times INT DEFAULT 0,
  happy_times INT DEFAULT 0,
  anger_times INT DEFAULT 0,
  bored_times INT DEFAULT 0,
  water_times INT DEFAULT 0,
  surprise_times INT DEFAULT 0,
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

ALTER TABLE articles ADD CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`) REFERENCES users(`username`);

CREATE TABLE visitors
(
  username CHAR(15) NOT NULL CHECK(username !=''),
  password CHAR(15) NOT NULL,
  nickname VARCHAR(20) NOT NULL UNIQUE,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(username)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

CREATE TABLE comments
(
  id INT NOT NULL AUTO_INCREMENT,
  content TEXT NOT NULL,
  article_id INT NOT NULL ,
  create_date DATE NOT NULL ,
  modify_date DATE ,
  PRIMARY KEY(id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 AUTO_INCREMENT = 1;

ALTER TABLE comments ADD CONSTRAINT `FK_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES articles(`id`);




INSERT INTO users (username,PASSWORD,email,nickname,create_date,modify_date)
VALUES ("zuoxiaolong","111111","223123@qq.com","nick","2015-02-01 00:00:00","2015-02-01 00:00:00");

INSERT INTO articles (username,icon,create_date,modify_date,
access_times,comment_times,good_times,touch_times,funny_times,happy_times,
anger_times,bored_times,water_times,surprise_times,SUBJECT,content)
VALUES
("zuoxiaolong","图标","2015-03-04 00:00:00","2015-03-04 00:00:00",
1,1,1,1,1,1,1,1,1,1,"这个主题其实应该稍微长一点",'<h3><span style="font-size: 14px;">引言</span></h3>
<p><span style="font-size: 14px;">　　</span></p>
<p><span style="font-size: 14px;">　　五一假期就要结束了，相信不少人这几天都去了同样一个景点，它叫人山人海。不同的是，LZ只有出租屋与几盒烟外加一台电脑的相伴。最近这一段时间，LZ除了在写一个redis的客户端以外，从去年年前就一直在思考一个问题，就是路在何方？</span></p>
<p><span style="font-size: 14px;">　　这个问题LZ已经很久没有思考过了，因为之前LZ的答案是，技术就是未来的路，这种信念很坚定。不过最近这一段时间，LZ有点恐惧了，开始对自己规划的路线产生了怀疑，这让LZ很久都没能静下心来看看技术书籍。</span></p>
<p><span style="font-size: 14px;">　　今天趁着五一最后一天假期，LZ就和各位闲聊一下最近的想法。</span></p>
<p><span style="font-size: 14px;">　　</span></p>
<h3><span style="font-size: 14px;">恐惧</span></h3>
<p><span style="font-size: 14px;">　　</span></p>
<p><span style="font-size: 14px;">　　刚才LZ提到了恐惧这个词，之所以用这个词，是因为觉得这个词十分的贴切。</span></p>
<p><span style="font-size: 14px;">　　之前，LZ觉得对于自己来说，学好技术，就只需要搞定JVM，搞定ssh，搞定设计模式等等，就基本上称为一个还算可以的技术小拿。但是随着LZ看的书越来越多，却感觉自己会的东西越来越少，需要学习的东西太多，多到不敢想象。所以内心真的是有一丝恐惧。</span></p>
<p><span style="font-size: 14px;">　　记得曾经大学里的一位教授说过一句话，或许这句话也不是出自他口，但甭管谁说的，LZ至今记忆犹新。</span></p>
<p><span style="font-size: 14px;">&nbsp;</span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px;"><em>　　</em><span style="text-decoration: underline;"><em>大概的意思就是，你掌握的知识是一个圈，圈内是你会的领域，圈外是你不会的领域。当你知识很狭隘的时候，你的圈很小，所以接触到的未知领域很小，这个时候，虽然你知识不够丰富，但往往会觉得自己很厉害。当你的知识越来越丰富，你的圈子越来越大，圈子所接触到的未知领域就会越来越大，这个时候虽然你的知识更加丰富了，但你却会更加低调，因为你会意识到，你不会的东西其实太多。</em></span></span></p>
<p><span style="font-size: 14px;"><span style="text-decoration: underline;"><em><img src="http://images.cnitblog.com/blog2015/558323/201505/041241288604223.png" alt="" width="410" height="274" /></em></span><em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </em><span style="text-decoration: underline;"><em><img src="http://images.cnitblog.com/blog2015/558323/201505/041241531102548.png" alt="" width="423" height="281" /></em></span></span></p>
<p><span style="font-size: 14px;"><em>　　</em><span style="text-decoration: underline;"><em>总结一句话，就是随着你知识越来越丰富，你会发现你不会的越来越多。所以这也是为什么真正的大神往往是很低调的，反而是一些水平很垃圾的人，容易觉得自己很牛B。</em></span></span></p>
<p><span style="font-size: 14px;">&nbsp;</span></p>
<p><span style="font-size: 14px;">　　回到正题，LZ最近发现，要成为一个真正的无所不能的大神，要掌握的东西实在太多太多。就说LZ，作为一个Java工程师，除了要了解Java圈所有的知识以外，很多时候还需要你了解很多周边知识，比如网络，通信协议，计算机原理，分布式等等，也需要了解一些其它语言，比如C/C++，Python，shell等等，一些基础当然也不能拉下，比如算法，编译原理等等。这些还只是LZ目前发现的而已，或许还有很多很多是LZ还没有提到的。</span></p>
<p><span style="font-size: 14px;">　　LZ就在想，自己真能把这些全部搞定吗？真的能全部精通或者说掌握吗？</span></p>
<p><span style="font-size: 14px;">　　以前LZ觉得只要自己坚持不懈的看下去，就一定可以，毕竟LZ的理解能力还不错，应该能一一啃下。但现在LZ不这么想了，或者说有点怀疑这种做法是否正确。基础很重要这点无可厚非，但LZ越来越怀疑花费大量的时间去看一些基础是否真的有这个必要。这个问题又回到了万年不变的一个问题上面去，考研是否值得。</span></p>
<p><span style="font-size: 14px;">　　之所以提到考研，是因为考研和这个问题一样，包括之前LZ的那篇重复造轮子的博文也是一样，这几类问题归根结底都是一类问题，就是&ldquo;把时间花费到XXX事情上面去，是否值得&rdquo;。比如&ldquo;把时间花费到考研和上研究生上面去，是否值得&rdquo;，再比如&ldquo;把时间花费到重复造轮子上面去，是否值得&rdquo;以及&ldquo;把时间花费到研究大量的基础知识上面去，是否值得&rdquo;。</span></p>
<p><span style="font-size: 14px;">　　</span></p>
<h3><span style="font-size: 14px;">以利益为立脚点</span></h3>
<p><span style="font-size: 14px;">　　</span></p>
<p><span style="font-size: 14px;">　　&ldquo;值不值得&rdquo;这个问题，肯定是需要以利益为立脚点，根本问题就在于，是否能满足你的需要。</span></p>
<p><span style="font-size: 14px;">　　每个人的追求都不一样，这也导致很多问题的答案并不是固定的，比如考研一定比不考好吗，重复造轮子一定比不造好吗，大公司一定比小公司好吗，上班一定比创业好吗这类问题。它们的答案对于每一个人来说都是不一样的，甚至对于同一个人的不同阶段都是不一样的。</span></p>
<p><span style="font-size: 14px;">　　有些人追求的是金钱上的满足，一句话，哪个赚钱我选哪个。有些人追求的是精神上的满足，一句话，哪个有成就感我选哪个。有些人追求的是稳定安逸，一句话，哪个可以躺赢我选哪个。有些人追求的是刺激，一句话，哪个玩命我选哪个。</span></p>
<p><span style="font-size: 14px;">　　那么选择就很简单了，根据自己的追求去选择，才是最正确的。或许说到这里，有的猿友会说，&ldquo;LZ你这不是在扯淡吗，让我们自己选这不是废话吗，等于没说&rdquo;。这样说也无可厚非，毕竟路总是要自己走的，决定总是要自己做的，这个谁都帮不了你，但是LZ可以尽我所能帮助大家。</span></p>
<p><span style="font-size: 14px;">　　比如，当你不知道你到底追求的是金钱还是成就感的时候，就问一下自己这个问题，&ldquo;亿万富翁和做出一个自己理想中但并不挣钱的项目你更喜欢哪一种&rdquo;。再比如，当你不知道你适合创业还是适合上班的时候，问一下自己，&ldquo;如果明天你就死了，你是否会后悔自己没有创业&rdquo;。</span></p>
<p><span style="font-size: 14px;">　　LZ觉得，当这个问题出现在你心中的时候，你潜意识当中的第一个选择，就是你的选择。这种选择是感性的，是跟随内心的，因为这个选择是你的第一反应，没有经过任何理性的思考。LZ个人觉得，30岁（具体多少岁因人而异）之前，跟随内心的选择永远不会错。</span></p>
<p><span style="font-size: 14px;">　　</span></p>
<h3><span style="font-size: 14px;">答案</span></h3>
<p><span style="font-size: 14px;">　　</span></p>
<p><span style="font-size: 14px;">　　LZ问过自己这样的问题，很抱歉，LZ发现自己很现实，因为LZ内心毫无疑问的会选择成为亿万富翁，或者说会选择金钱。原因很简单，LZ实在太喜欢兰博基尼。那么现在问题就更清晰了，问题就是，&ldquo;疯狂的学习基础知识，比如算法，编译原理，网络，通信协议等等，是否能够让你挣的更多&rdquo;。</span></p>
<p><span style="font-size: 14px;">　　第一直觉告诉LZ，似乎这些并不能让LZ赚更多的钱。当然，LZ可没有说基础知识不重要，只是说对于LZ目前的情况来讲，基础知识的补足可能并不能很快的增加LZ的价值。毕竟LZ刚换工作不久，经历了不少面试，LZ并不觉得多答上一两道基础知识的问题可以大大增加LZ的薪水。</span></p>
<p><span style="font-size: 14px;">　　那么问题来了，在目前的阶段，到底补充什么知识可以大大增加自己的薪水呢？</span></p>
<p><span style="font-size: 14px;">　　目前LZ思考了很久，最终选择的答案是金融知识（备注：选择金融知识是因为LZ做的是互联网金融行业），所以在不久之后，LZ很大可能会去看一些金融方面的书籍，暂时放弃去啃技术基础。到底这样做是否正确，LZ不敢确定，只能走一步算一步，毕竟自己的路只能自己走出来。</span></p>
<p><span style="font-size: 14px;">　　</span></p>
<h3><span style="font-size: 14px;">后话</span></h3>
<p><span style="font-size: 14px;">　　</span></p>
<p><span style="font-size: 14px;">　　尽管LZ不确定自己思考的答案是否真的正确，以及是否真的适合自己，但LZ觉得类似于这样的思考非常重要。很多人喜欢盲目跟风，缺乏自己的思考，这样的人注定只能跟着别人喝汤。</span></p>
<p><span style="font-size: 14px;">　　因此，不管对与错，经常思考一下自己的路线，是非常有必要的，这比你多学一个算法，多看一本书绝对要重要太多。</span></p>');

