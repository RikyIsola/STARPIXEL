package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class PortaVirus extends Solido
{
	public int immagine()
	{
		return R.drawable.portavirus;
	}
	public String nomeItaliano()
	{
		return"PORTA VIRUS";
	}
	public String nomeInglese()
	{
		return"VIRUS CONTAINER";
	}
	public String descrizioneItaliano()
	{
		return"QUESTO CONTENITORE CONTIENE IL POTENTE VIRUS CHE TRASFORMA GLI ESSERI VIVEMTI IN ZOMBIE";
	}
	public String descrizioneInglese()
	{
		return"THIS CONTAINER CONTAINS THE POWERFULL VIRUS THAT TRANSFORM EVERY LIFE FORM INTO ZOMBIE";
	}
	public int vita()
	{
		return 100;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[]{b.Virus};
	}
}
