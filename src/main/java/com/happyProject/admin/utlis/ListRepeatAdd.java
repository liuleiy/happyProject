package com.happyProject.admin.utlis;

import java.util.ArrayList;
import java.util.List;

import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.Roles;
import com.happyProject.admin.model.TopUpRanking;

public class ListRepeatAdd {
	public static List<Roles> repeatAddRoles(List<Roles> newTimeUser){
		for (int i = 0; i < newTimeUser.size(); i++) {
			int count = 0; //计数器  
			for (int j = i + 1; j < newTimeUser.size(); j++) {
				if(newTimeUser.get(i).getUserid().equals(newTimeUser.get(j).getUserid())){
					count++;//i位和i+1位对比，如果相同，计数器应该+1  
				}else{
				 break;
				}
			}
			int tempScore = newTimeUser.get(i).getScore();//取起始位置的输赢  
            for (int j = 0; j < count; j++) {//这个count就是重复值的数量？  
                tempScore += newTimeUser.get(i+1).getScore();//计数器记录了后面有几个相同的,循环count 起始位置+count(j) + 1  
                newTimeUser.remove(i + 1); // List是动态数组，循环count，删除i+1即可  
            }  
            newTimeUser.get(i).setScore(tempScore);//出重已经完成，重新赋值即可  
		}
		return newTimeUser;
	}
	
	public static List<Order> tradeRecordAdd(List<Order> newTimeUser){
		for (int i = 0; i < newTimeUser.size(); i++) {
			int count = 0; //计数器  
			for (int j = i + 1; j < newTimeUser.size(); j++) {
				if(newTimeUser.get(i).getUserid().equals(newTimeUser.get(j).getUserid())){
					count++;//i位和i+1位对比，如果相同，计数器应该+1  
				}else{
				 break;
				}
			}
			int tempScore = 0 ;
			tempScore = (int) (newTimeUser.get(i).getMoney()*100);//取起始位置的输赢  
            for (int j = 0; j < count; j++) {//这个count就是重复值的数量？  
                tempScore += newTimeUser.get(i+1).getMoney()*100;//计数器记录了后面有几个相同的,循环count 起始位置+count(j) + 1  
                newTimeUser.remove(i + 1); // List是动态数组，循环count，删除i+1即可  
            }
            
            
            newTimeUser.get(i).setMoney((double)((double)tempScore/100));//出重已经完成，重新赋值即可  
		}
		return newTimeUser;
	}
	
	public static List<TopUpRanking> getTopUpRanking(List<Order> newTimeUser){
		List<TopUpRanking> list = new ArrayList<TopUpRanking>();
		for (int i = 0; i < newTimeUser.size(); i++) {
			int count = 0; //计数器  
			for (int j = i + 1; j < newTimeUser.size(); j++) {
				if(newTimeUser.get(i).getUserid().equals(newTimeUser.get(j).getUserid())){
					count++;//i位和i+1位对比，如果相同，计数器应该+1 
					
				}else{
				 break;
				}
			}
			int tempScore = 0 ;
			
			tempScore = (int) (newTimeUser.get(i).getMoney()*100);//取起始位置的输赢  
			
			for (int j = 0; j < count; j++) {//这个count就是重复值的数量？  
                tempScore += newTimeUser.get(i+1).getMoney()*100;//计数器记录了后面有几个相同的,循环count 起始位置+count(j) + 1  
                newTimeUser.remove(i + 1); // List是动态数组，循环count，删除i+1即可  
            }
            TopUpRanking topUpRanking = new TopUpRanking();
            topUpRanking.setUserid(newTimeUser.get(i).getUserid());//userid
            topUpRanking.setTopUp((double)((double)tempScore/100));//充值
            topUpRanking.setOrderNumber(count+1);//订单数
            list.add(topUpRanking);
            newTimeUser.get(i).setMoney((double)((double)tempScore/100));//出重已经完成，重新赋值即可  
		}
		return list;
		
	}
	
}
