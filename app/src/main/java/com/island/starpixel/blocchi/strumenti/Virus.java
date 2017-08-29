package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
public class Virus extends Strumento
{
	public int immagine()
	{
		return R.drawable.virus;
	}
	public String descrizioneItaliano()
	{
		return"TRASFORMA TUTTO CIO CHE TOCCA IN ZOMBIE";
	}
	public String descrizioneInglese()
	{
		return"IT TRANSFORM EVERYTHING IT TOUCHES INTO ZOMBIE";
	}
	public String nomeItaliano()
	{
		return"VIRUS";
	}
	public String nomeInglese()
	{
		return"VIRUS";
	}
	public int vita()
	{
		return 20;
	}
}
