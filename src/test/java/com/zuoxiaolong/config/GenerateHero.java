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

	public static void main(String[] args) {
		String[] herosArray = heros.split("，");
		for (int i = 0; i < herosArray.length; i++) {
			System.out.println("insert into hero (full_name) values ('"+herosArray[i]+"');");
		}
	}
}
