/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author 左潇龙
 * @since 2015年5月18日 下午11:50:19
 */
package com.zuoxiaolong.config;

/**
 * @author zuoxiaolong
 *
 */
public class GenerateHero {
	
	static String heros = "天怒法师，兽王，赏金猎人，仙女龙，吸血鬼，军团指挥，炸弹人，龙骑士，流浪剑客，末日使者，机甲浣熊，冰魂，幻影刺客，树精卫士，风暴之灵，灰烬之灵，老鹿，白牛，巫医，猴子，召唤师，凤凰，机枪兵，巨魔，术士，灵魂守卫，敌法，死亡先知，熊猫酒仙，亚龙，直升机，白虎，复仇，骨弓，风行，火枪，光法，小黑，小鹿，双头龙，神灵武士，月骑，沉默，影魔，恶魔巫师，美杜莎，黑鸟，暗牧，冰女，修补匠，骨法，巫妖，火女，宙斯，痛苦女王，死骑，小小，斧王，剑圣，死灵法师，小娜迦，拍拍熊，骷髅王，神牛，电魂，大鱼人，蓝胖，潮汐，全能骑士，船长";

	static String aliases = "天怒法师-tnfs-tiannufashi，兽王-sw-shouwang，赏金猎人-sjlr-shangjinlieren，仙女龙-puck-xnl-xiannvlong，吸血鬼-xxg-xixuegui，军团指挥-jtzh-juntuanzhihui，炸弹人-zdr-zhadanren，龙骑士-lqs-dk-longqishi，流浪剑客-seven-lljk-liulangjianke，末日使者-mrsz-morishizhe，机甲浣熊-小碗熊-jjwx-xwx-jijiawanxiong-xiaowanxiong，冰魂-bh-binghun，幻影刺客-pa-hyck-huanyingcike，树精卫士-sjws-shujingweishi，风暴之灵-fbzl-蓝猫-lm-fengbaozhiling-lanmao，灰烬之灵-hjzl-火猫-hm-huijinzhiling-huomao，老鹿-ll-ts-laolu，白牛-bn-sb-bainiu，巫医-51-wy-wuyi，猴子-hz-houzi，召唤师-卡尔-zhs-ke-zhaohuanshi-kaer，凤凰-fh-fenghuang，机枪兵-jqb-jiqiangbing，巨魔-jm-jumo，术士-ss-shushi，灵魂守卫-lhsw-tb-linghunshouwei，敌法-df-difa，死亡先知-swxz-siwangxianzhi，熊猫酒仙-xmjx-xiongmaojiuxian-xiongmiaojiuxian，亚龙-yl-vip-vp-yalong，直升机-飞机-zsj-fj-zhishengji-feiji，白虎-bh-baihu，复仇-vs-fc-fuchou，骨弓-gg-gugong，风行-fx-fengxing，火枪-hq-xp-小炮-huoqiang-xiaopao，光法-gf-guangfa，小黑-xh-xiaohei，小鹿-xl-xiaolu，双头龙-stl-shuangtoulong，神灵武士-slws-shenlingwushi，月骑-yq-yueqi，沉默-cm-chenmo，影魔-ym-sf-yingmo，恶魔巫师-lion-emws-emowushi，美杜莎-一姐-大娜迦-mds-yj-dnj-meidusha-yijie-danajia，黑鸟-hn-heiniao，暗牧-am-anmu，冰女-bn-bingnv，修补匠-xbj-tk-xiubujiang，骨法-gf-gufa，巫妖-51-wy-wuyao，火女-hn-huonv，宙斯-zs-zhousi，痛苦女王-tknw-tongkunvwang，死骑-sq-siqi，小小-xx-xiaoxiao，斧王-fw-fuwang，剑圣-js-jiansheng，死灵法师-slfs-silingfashi，小娜迦-xnj-xiaonajia，拍拍熊-ppx-paipaixiong，骷髅王-klw-kulouwang，神牛-es-sn-shenniu，电魂-dh-dianhun，大鱼人-dyr-dayuren，蓝胖-lp-lanpang，潮汐-cx-chaoxi，全能骑士-qnqs-quannengqishi，船长-cz-chuanzhang";

	public static void main(String[] args) {
//		String[] herosArray = heros.split("，");
//		for (int i = 0; i < herosArray.length; i++) {
//			System.out.println("insert into hero (full_name) values ('"+herosArray[i]+"');");
//		}
		String[] aliasesArray = aliases.split("，");
		for (int i = 0; i < aliasesArray.length; i++) {
			String[] aliases = aliasesArray[i].split("\\-");
			String fullname= aliases[0];
			String temp = aliases[1];
			if (temp.toLowerCase().matches("^[a-z]+$")) {
				temp += "," + temp.toUpperCase();
			}
			for (int j = 2; j < aliases.length; j++) {
				temp += "," + aliases[j].toLowerCase();
				if (aliases[j].toLowerCase().matches("^[a-z]+$")) {
					temp += "," + aliases[j].toUpperCase();
				}
			}
			System.out.println("update hero set aliases='" + temp + "' where full_name='" + fullname + "';");
		}
	}
}
