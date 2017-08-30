package com.island;
import java.lang.reflect.*;
import java.util.*;
public class Lista<T>extends ArrayList<T>
{
	public static final char puntoc=".".toCharArray()[0];
	public static final char menoc="-".toCharArray()[0];
	public static final char zeroc="0".toCharArray()[0];
	public static final char unoc="1".toCharArray()[0];
	public static final char duec="2".toCharArray()[0];
	public static final char trec="3".toCharArray()[0];
	public static final char quattroc="4".toCharArray()[0];
	public static final char cinquec="5".toCharArray()[0];
	public static final char seic="6".toCharArray()[0];
	public static final char settec="7".toCharArray()[0];
	public static final char ottoc="8".toCharArray()[0];
	public static final char novec="9".toCharArray()[0];
	public static final String punto=".";
	public static final String at="at ";
	public static final String tonda="(";
	public static final String tonda2=")";
	public static final String punti=":";
	public static final String nativo="Native Method";
	public static final String linea=System.lineSeparator();
	public static final String debugNonChiuso="DEBUG NON CHIUSO ID DOVEVA ESSERE:";
	public static final String inveceE=" INVECE È:";
	public static final String vel="VEL:";
	public static final String vel1=" ms MED ";
	public static final String vel2=" ms MIN ";
	public static final String vel3=" ms MAX ";
	public static final String memoria=" ms RAM:";
	public static final String memoria1=" kb MED ";
	public static final String memoria2=" kb MIN ";
	public static final String memoria3=" kb MAX ";
	public static final String unita=" kb ";
	public static final String unita2=" b";
	public static final String risultati="DEBUGGER MANAGER RESULTS:";
	public static final String vero="true";
	public static final String falso="false";
	public static final String Boolean="boolean";
	public static final String Byte="byte";
	public static final String Char="char";
	public static final String Long="long";
	public static final String Double="double";
	public static final String Float="float";
	public static final String Short="short";
	public static final String Int="int";
	public static final String huawei="HUAWEI";
	public static final String fps="FPS:";
	public static final String fps2="MAIN:";
	public static final String speed="VEL:";
	public static final String sound="SUONO:";
	public static final String ram="RAM:";
	public static final String garbage="GC:";
	public static final String networkt="NETT:";
	public static final String networkr="NETR:";
	public static final byte lineabyte=linea.getBytes()[0];
	public static final char lineachar=linea.charAt(0);
	public static final String cancella="<";
	public static double toDouble(CharSequence cs)
	{
		int length=cs.length();
		boolean negativo=false;
		boolean puntino=false;
		double ritorno=0;
		int index=1;
		for(int a=0;a<length;a++)
		{
			char c=cs.charAt(a);
			if(c==menoc)negativo=true;
			else
			{
				if(c==zeroc)ritorno+=0.0/index;
				else if(c==unoc)ritorno+=1.0/index;
				else if(c==duec)ritorno+=2.0/index;
				else if(c==trec)ritorno+=3.0/index;
				else if(c==quattroc)ritorno+=4.0/index;
				else if(c==cinquec)ritorno+=5.0/index;
				else if(c==seic)ritorno+=6.0/index;
				else if(c==settec)ritorno+=7.0/index;
				else if(c==ottoc)ritorno+=8.0/index;
				else if(c==novec)ritorno+=9.0/index;
				else if(c==puntoc)
				{
					ritorno/=10;
					puntino=true;
				}
				else throw new NumberFormatException();
				if(!puntino)ritorno*=10;
				else index*=10;
			}
		}
		if(negativo)ritorno*=-1;
		if(!puntino)ritorno/=10;
		return ritorno;
	}
	public static int toInt(CharSequence cs)
	{
		return (int)toLong(cs);
	}
	public static long toLong(CharSequence cs)
	{
		int length=cs.length();
		boolean negativo=false;
		long ritorno=0;
		int index=1;
		for(int a=0;a<length;a++)
		{
			char c=cs.charAt(a);
			if(c==menoc)negativo=true;
			else
			{
				if(c==zeroc)ritorno+=0.0/index;
				else if(c==unoc)ritorno+=1.0/index;
				else if(c==duec)ritorno+=2.0/index;
				else if(c==trec)ritorno+=3.0/index;
				else if(c==quattroc)ritorno+=4.0/index;
				else if(c==cinquec)ritorno+=5.0/index;
				else if(c==seic)ritorno+=6.0/index;
				else if(c==settec)ritorno+=7.0/index;
				else if(c==ottoc)ritorno+=8.0/index;
				else if(c==novec)ritorno+=9.0/index;
				else throw new NumberFormatException();
				ritorno*=10;
			}
		}
		if(negativo)ritorno*=-1;
		ritorno/=10;
		return ritorno;
	}
	public static boolean uguali(CharSequence uno,CharSequence due)
	{
		if(uno.length()!=due.length())return false;
		else
		{
			int length=uno.length();
			for(int a=0;a<length;a++)if(uno.charAt(a)!=due.charAt(a))return false;
			return true;
		}
	}
	public static void debug(Throwable t)
	{
		StackTraceElement[]ste=t.getStackTrace();
		StringBuilder sb=new StringBuilder((ste.length+1)*42);
		sb.append(t);
		for(StackTraceElement el:ste)
		{
			sb.append(linea).append(at).append(el.getClassName()).append(punto).append(el.getMethodName()).append(tonda);
			int n=el.getLineNumber();
			if(n==-2)sb.append(nativo);
			else sb.append(el.getFileName()).append(punti).append(n);
			sb.append(tonda2);
		}
		Throwable causa=t.getCause();
		if(causa!=null)sb.append(linea).append("Cause by:");
		System.err.println(sb);
		if(causa!=null)debug(causa);
	}
	public static void breakpoint(long tempo,Object[]valori)
	{
		try
		{
			Thread.sleep(tempo);
			for(Object o:valori)System.out.println(o);
		}
		catch(InterruptedException e)
		{
			debug(e);
		}
	}
	public static<T>Lista<T>aggiungi(Lista<T>input,T aggiungi)
	{
		input.add(aggiungi);
		return input;
	}
	public static<T>Lista<T>rimuovi(Lista<T>input,T rimuovi)
	{
		input.remove(rimuovi);
		return input;
	}
	public static<T>Lista<T>rimuovi(Lista<T>input,int index)
	{
		input.remove(index);
		return input;
	}
	public static double arrotonda(double valore,int cifre)
	{
		double potenza=(int)Math.pow(10,cifre);
		return Math.round(valore*potenza)/potenza;
	}
	public Lista(){}
	public Lista(int n){super(n);}
	public Lista(Collection<? extends T>n){super(n);}
	public Iterator iterator()
	{
		return new Iterator()
		{
			int n=size();
			public boolean hasNext()
			{
				return size()-n<size();
			}
			public Object next()
			{
				Object o=get(size()-n);
				n--;
				return o;
			}
			public void remove(){}
		};
	}
}
