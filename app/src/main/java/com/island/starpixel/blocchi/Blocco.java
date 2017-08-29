package com.island.starpixel.blocchi;
public abstract class Blocco
{
	public int id;
	public abstract int immagine()
	public abstract String nomeItaliano()
	public abstract String nomeInglese()
	public abstract String descrizioneItaliano()
	public abstract String descrizioneInglese()
	public abstract int vita()
	public String nomeItaliano,nomeInglese,descrizioneItaliano,descrizioneInglese,ids;
	public Blocco[]prodotti;
	public int forza()
	{
		return 1;
	}
	protected Blocco[]prodotti(Blocchi b)
	{
		return new Blocco[0];
	}
	public Blocco()
	{
		nomeItaliano=nomeItaliano();
		nomeInglese=nomeInglese();
		descrizioneItaliano=descrizioneItaliano();
		descrizioneInglese=descrizioneInglese();
	}
}
