package com.island.starpixel;
import android.graphics.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;
import java.io.*;
import android.os.*;
public class Descrizione extends Finestra
{
	Descrizione(Schermo schermo,Blocco b)
	{
		super(schermo);
		this.b=b;
		Gruppo g=new Gruppo(this,3,10);
		new Bottone(g,0,0,1,10,Color.TRANSPARENT,Color.TRANSPARENT).immagine(b.immagine());
		new Testo(g,1,0,3,2,schermo().lingua.nome(b)).larghezzaX(0.13);
		String tipo=null;
		if(b instanceof Terreno)tipo=schermo().lingua.terreno;
		new Testo(g,1,2,3,3,tipo).larghezzaX(0.25);
		new SuperTesto(g,1,3,3,10,schermo().lingua.descrizione(b)).carattere(0.05).larghezzaX(0.5).aggiorna();
		g.aggiorna();
	}
	private Blocco b;
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void salva(Bundle b)
	{
		b.putBoolean("DESCRIZIONE",true);
		b.putInt("DESCRIZIONE BLOCCO",this.b.id);
	}
}
