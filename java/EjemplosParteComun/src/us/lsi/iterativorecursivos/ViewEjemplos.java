package us.lsi.iterativorecursivos;

import java.util.Iterator;
import java.util.List;

import us.lsi.common.Ranges.IntRange;
import us.lsi.common.Ranges.LongRange;
import us.lsi.common.View1;
import us.lsi.common.View2;
import us.lsi.flujossecuenciales.Iterators;


public class ViewEjemplos {
	
	public static Integer sum(List<Integer>ls) {
		if(ls.isEmpty()) return 0;
		IntRange r = IntRange.of(0, ls.size());		
		View1<IntRange,Integer> w = r.view1();	
		Integer i = w.element();
		Integer sum = 0;
		while(r.size()>0) {					
			sum = sum +ls.get(i);
			r = w.rest();
			w = r.view1();
			i = w.element();
		}
		return sum;
	}
	
	public static Long sqrtLong(Long a) {
		LongRange r = LongRange.of(0L,a+1);
		View2<LongRange,Long> w = r.view2Overlapping();
		Long e = w.centralElement();
		Long ec = e*e;
		while(r.size()>2 && ec != a) {					
			if(a < ec) r = w.left();
			else r = w.right();
			w = r.view2Overlapping();
			e = w.centralElement();
			ec = e*e;
		}
		return ec == a? e: r.a;
	}
	
	public static int indexOf(List<Integer>ls, Integer elem) {
		IntRange r = IntRange.of(0, ls.size());		
		View2<IntRange,Integer> w = r.view2();			
		Integer i= w.centralElement();
		while(r.size()>1 && ls.get(i) != elem) {			
			if(elem < ls.get(i)) r = w.left();
			else r = w.right();
			w = r.view2();
			i = w.centralElement();
		}
		return ls.get(i) == elem? i: -1;
	}
	
	public static int masCercano(List<Integer>ls, Integer elem) {
		if(ls.isEmpty()) return -1;
		if(ls.size()==1) return ls.get(0);
		IntRange r = IntRange.of(0, ls.size());		
		View2<IntRange,Integer> w = r.view2Overlapping();			
		Integer i= w.centralElement();
		while(r.size()>2 && ls.get(i) != elem) {			
			if(elem < ls.get(i)) r = w.left();
			else r = w.right();
			w = r.view2Overlapping();
			i = w.centralElement();
		}
		return ls.get(i) == elem? i: masCercanoBase(ls,elem, r);
	}
	
	public static Integer masCercanoBase(List<Integer>ls, Integer elem, IntRange r) {
		Integer a = ls.get(r.a);
		Integer b = ls.get(r.a+1);
		if(Math.abs(elem-a) < Math.abs(elem-b)) return a;
		else return b;
	}
	
	public static int masCercano2(List<Integer>ls, Integer elem) {
		if(ls.isEmpty()) return -1;
		if(ls.size()==1) return ls.get(0);
		IntRange r = IntRange.of(0, ls.size());					
		return masCercano2(ls,elem,r);
	}
	
	public static Integer masCercano2(List<Integer>ls, Integer elem, IntRange r) {				
		Integer s;		
		if(r.size()<=2) {
			s = masCercanoBase(ls,elem, r);
		}  else {
			View2<IntRange,Integer> w = r.view2Overlapping();			
			Integer i = w.centralElement();
			if(ls.get(i) == elem) s = ls.get(i);
			else if(elem < ls.get(i)) s = masCercano2(ls,elem,w.left());
			else s = masCercano2(ls,elem,w.right());;
		}
		return s;
	}
	
	public static Integer sumIntegerOFile(String file) {
		Iterator<String> it = Iterators.file(file);
		if(!it.hasNext()) return 0;	
		Integer sum = 0;
		while(it.hasNext()) {
			View1<Iterator<String>,String> w = Iterators.view(it);
			Integer e = Integer.parseInt(w.element());
			it = w.rest();
			sum = sum + e;			
		}
		return sum;
	}
	
	public static void main(String[] args) {
		System.out.println(sum(List.of(1,3,7,9,31,54,91,102)));
		System.out.println(sqrtLong(102L));
		System.out.println(indexOf(List.of(1,3,7,9,31,54,91,102),92));
		System.out.println(masCercano(List.of(1,3,7,9,31,54,91,102),90));
		System.out.println(masCercano2(List.of(1,3,7,9,31,54,91,102),90));
		System.out.println(sumIntegerOFile("ficheros/numeros.txt"));
	}

}
