import org.apache.commons.lang3.StringUtils;

import co.wnk.framework.core.common.util.WnkStringUtils;

public class utilsTest {

	public static void main(String[] args) throws Exception {
		String[] arr = WnkStringUtils.split("1111,2222,3333", null);
		for(String str : arr){
			System.out.println("str : " + str);
		}
		
		System.out.println(StringUtils.upperCase(null));
	}

}
