package BL.blhelper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import vopo.SharesData;

public class SortHelper {
	
	
	
	/**
	 * @param prime
	 * @param standard
	 * @return 截取map.将prime中standard没有的项删除
	 */
	public Map<String, Double> subMapSame(Map<String, Double> prime,Map<String, Double> standard){
		ArrayList<String> removeKey = new ArrayList<>();
		for(String key:prime.keySet()){
			if(!standard.containsKey(key)){
				removeKey.add(key);
			}
		}
		for(String key:removeKey){
			prime.remove(key);
		}
		return sortByDate(prime);
		
	}
	/**
	 * @param prime
	 * @param standard
	 * @return 填充map.将prime中standard没有的项置为后一项的值
	 */
	public Map<String, Double> fillMapSame(Map<String, Double> prime,Map<String, Double> standard){
		prime = sortByDate(prime);
		standard = sortByDate(standard);
		ArrayList<String> fillKey = new ArrayList<>();
		ArrayList<Double> fillVal = new ArrayList<>();
		DateDeal dateDeal = new DateDeal();
		boolean getVal = false;
		
		String maxKey = getLastObject(prime);
		for(String key:standard.keySet()){
			if(getVal){
				for(String key2:prime.keySet()){
					if(dateDeal.dateCompare(key2, key)){
						maxKey = key2;
						break;
					}
				}
				fillVal.add(prime.get(maxKey));
				maxKey = getLastObject(prime);
				getVal = false;
			}
			if(!prime.containsKey(key)){
				fillKey.add(key);
				getVal = true;
			}
			
		}
		if(fillVal.size()<fillKey.size()){
			fillVal.add(prime.get(maxKey));
		}
		for(int i=0;i<fillKey.size();i++){
			String nowKey = fillKey.get(i);
			double nowVal = fillVal.get(i);
			prime.put(nowKey, nowVal);
		}
		return sortByDate(prime);
		
	}
	
	
	/**
	 * @param prime
	 * @param date
	 * @return 截取map到指定的日期
	 */
	public Map<String, Double> subMap(Map<String, Double> prime,String date){
		DateDeal dateDeal = new DateDeal();
		Map<String,Double> result = new HashMap<>();
		for(String key:prime.keySet()){
			
			result.put(key, prime.get(key));
			if(key.equals(date)){
				break;
			}
			if(dateDeal.dateCompare(key, date)){
				result.remove(key);
				break;
			}
		}
		result = sortByDate(result);
		return result;
	}
	/**
	 * @param prime
	 * @return 将map转化为list
	 */
	public ArrayList<Double> mapToList(Map<String, Double> prime){
		ArrayList<Double> result = new ArrayList<>();
		for(String key:prime.keySet()){
			result.add(prime.get(key));
		}
		return result;
	}
	
	/**
	 * @param prime
	 * @return 将map的key转为list
	 */
	public ArrayList<String> mapKeyToArray(Map<String, String> prime){
		ArrayList<String> result = new ArrayList<>();
		for(String key:prime.keySet()){
			result.add(key);
		}
		return result;
	}
	
	/**
	 * @param acculRise
	 * @return 累计收益率转为成当日收益率
	 */
	public ArrayList<Double> acculToNorm(ArrayList<Double> acculRise){
		ArrayList<Double> result = new ArrayList<>();
		double first = 1.0;
		for(Double rise:acculRise){
			double nowRise = rise+1.0;
			double change = (nowRise-first)/first-1.0;
			result.add(change);
			first = nowRise;
		}
		return result;
	}
	
	/**
	 * @param map
	 * @return 得到第一个键值
	 */
	public String getFirstObject(Map<String, Double> map){
		Iterator<String> iterator = map.keySet().iterator();
		String result = "";
		while(iterator.hasNext()){
			result = iterator.next();
			break;
		}
		return result;
	}
	
	
	/**
	 * @param map
	 * @return 得到最后一日
	 */
	public String getLastObject(Map<String, Double> map){
		Iterator<String> iterator = map.keySet().iterator();
		String result = "";
		while(iterator.hasNext()){
			result = iterator.next();
		}
		return result;
	}
	/**
	 * @param primeMap
	 * @return 按照股票id大小排序
	 */
	public Map<String, String> sortById(Map<String, String> primeMap){
		List<Map.Entry<String,String>> mappingList = null;
		mappingList = new ArrayList<Map.Entry<String,String>>(primeMap.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String,String>>(){
			
			   public int compare(Map.Entry<String,String> mapping1,Map.Entry<String,String> mapping2){ 
			   DateDeal dateDeal = new DateDeal();
			   //try{
			   int date1 = Integer.valueOf(mapping1.getKey());
			   int date2 = Integer.valueOf(mapping2.getKey());
			   if(date1>date2){
				   return 1;
			   }
			   else if(date1==date2){
				   return 0;
			   }
			   else{
				   return -1;
			   }
			   /*}catch (Exception e) {
				System.out.println(mapping1.getValue());
				System.out.println(mapping2.getValue());
				return 1;// TODO: handle exception
			}*/
			   } 
			  }); 
		Map<String, String> result = new LinkedHashMap<>();
		for(Map.Entry<String,String> mapping:mappingList){
			result.put(mapping.getKey(), mapping.getValue());
		}
		return result;
	}
	
	/**
	 * @param primeList
	 * @return 根据日期将列表排序
	 */
	public ArrayList<SharesData> sortListByDate(ArrayList<SharesData> primeList){
		Collections.sort(primeList,new Comparator<SharesData>() {

			@Override
			public int compare(SharesData o1, SharesData o2) {
				String date1 = o1.date;
				String date2 = o2.date;
				 DateDeal dateDeal = new DateDeal();
				if(dateDeal.dateCompare(date1, date2)){
					   return 1;
				   }
				   else if(date1.equals(date2)){
					   return 0;
				   }
				   else{
					   return -1;
				   }
			}

			
		});
		return primeList;
	}
	/**
	 * @param primeMap
	 * @return 按照收益率排序
	 */
	public Map<String, Double> sortByRise(Map<String, Double> primeMap){
		List<Map.Entry<String,Double>> mappingList = null;
		mappingList = new ArrayList<Map.Entry<String,Double>>(primeMap.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){ 
			   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){ 
			   double rise1 = mapping1.getValue();
			   double rise2 = mapping2.getValue();
			   if(rise1>rise2){
				   return -1;
			   }
			   else if(rise1==rise2){
				   return 0;
			   }
			   else{
				   return 1;
			   }
			   } 
			  }); 
		Map<String, Double> result = new LinkedHashMap<>();
		for(Map.Entry<String,Double> mapping:mappingList){
			result.put(mapping.getKey(), mapping.getValue());
		}
		return result;
	}
	/**
	 * @return 按照日期排序
	 */
	public Map<String, Double> sortByDate(Map<String, Double> primeMap){
		List<Map.Entry<String,Double>> mappingList = null;
		mappingList = new ArrayList<Map.Entry<String,Double>>(primeMap.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String,Double>>(){ 
			   public int compare(Map.Entry<String,Double> mapping1,Map.Entry<String,Double> mapping2){ 
			   DateDeal dateDeal = new DateDeal();
			   String date1 = mapping1.getKey();
			   String date2 = mapping2.getKey();
			   if(dateDeal.dateCompare(date1, date2)){
				   return 1;
			   }
			   else if(date1.equals(date2)){
				   return 0;
			   }
			   else{
				   return -1;
			   }
			   } 
			  }); 
		Map<String, Double> result = new LinkedHashMap<>();
		for(Map.Entry<String,Double> mapping:mappingList){
			result.put(mapping.getKey(), mapping.getValue());
		}
		return result;
	}

}
