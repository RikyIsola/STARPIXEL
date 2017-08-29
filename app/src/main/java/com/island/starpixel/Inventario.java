package com.island.starpixel;
import android.view.*;
import com.island.*;
import com.island.starpixel.blocchi.*;
import java.io.*;
import android.graphics.*;
public class Inventario extends Finestra
{
	private Lan lan;
	private Bottone bottone,selezionato;
	private Gruppo g,inventario,costruisci,scelte;
	int index;
	private boolean finito;
	Inventario(Schermo schermo,Lan lan,Bottone bottone)
	{
		super(schermo);
		g=new Gruppo(this,10,10);
		selezionato=new Bottone(g,0,0,5,1,Color.rgb(0,155,0),Color.GREEN);
		selezionato.scrivi(schermo().lingua.inventario).setOnClickListener(click());
		new Bottone(g,5,0,10,1,Color.RED,Color.rgb(155,0,0)).scrivi(schermo().lingua.costruisci).setOnClickListener(click());
		new Testo(g,0,1,10,2,schermo().lingua.toccaALungo).larghezzaX(0.75);
		inventario=new Gruppo(g,0,2,10,10,10,8).colore(Color.rgb(0,155,0));
		costruisci=new Gruppo(g,0,1,10,10,10,9).colore(Color.rgb(155,0,0));
		costruisci.setVisibility(View.INVISIBLE);
		scelte=new Gruppo(costruisci,5,0,9,10,5,10).antiTrans(true,true);
		g.aggiorna();
		this.lan=lan;
		this.bottone=bottone;
		lan.manda(schermo().blocchi.inventariolan);
	}
	void aggiungi(int aggiunta)
	{
		final Blocco b=schermo().blocchi.blocchi[aggiunta];
		final int n=index;
		schermo().runOnUiThread(new Runnable()
		{
			public void run()
			{
				View v=new Bottone(inventario,1+n%8,n/8,2+n%8,n/8+1).immagine(b.immagine());
				v.setOnClickListener(click(n));
				v.setOnLongClickListener(lungo(b));
				v=new Bottone(costruisci,1+n%4,n/4,2+n%4,n/4+1).immagine(b.immagine());
				v.setOnClickListener(costruisci(b));
				v.setOnLongClickListener(lungo(b));
				g.aggiorna();
			}
		});
		index++;
	}
	void finito()
	{
		finito=true;
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void onBackPressed()
	{
		if(finito)super.onBackPressed();
	}
	private View.OnClickListener click(final int n)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(finito)
				{
					bottone.immagine(((Bottone)p1).immagine());
					lan.manda(schermo().blocchi.selezionato);
					lan.manda(String.valueOf(n));
					cancel();
				}
			}
		};
	}
	private View.OnClickListener costruisci(final Blocco b)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				scelte.removeAllViews();
				Blocco[]prodotti=b.prodotti;
				for(int a=0;a<prodotti.length;a++)
				{
					View v=new Bottone(scelte,1+a%4,a/4,2+a%4,a/4+1).immagine(prodotti[a].immagine());
					v.setOnClickListener(crea(prodotti[a],b));
					v.setOnLongClickListener(lungo(prodotti[a]));
				}
				scelte.aggiorna();
			}
		};
	}
	private View.OnClickListener click()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				Bottone b=(Bottone)p1;
				if(b!=selezionato)
				{
					if(b.coloreRilascio()==Color.RED)
					{
						selezionato.colore(Color.GREEN,Color.rgb(0,155,0));
						b.colore(Color.rgb(155,0,0),Color.RED);
						costruisci.setVisibility(View.VISIBLE);
						inventario.setVisibility(View.INVISIBLE);
					}
					else
					{
						selezionato.colore(Color.RED,Color.rgb(155,0,0));
						b.colore(Color.rgb(0,155,0),Color.GREEN);
						inventario.setVisibility(View.VISIBLE);
						costruisci.setVisibility(View.INVISIBLE);
					}
					selezionato=b;
					g.aggiorna();
				}
			}
		};
	}
	private View.OnClickListener crea(final Blocco b,final Blocco iniziale)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				if(finito)
				{
					lan.manda(schermo().blocchi.creazione);
					lan.manda(iniziale.ids);
					lan.manda(b.ids);
					cancel();
				}
			}
		};
	}
	private View.OnLongClickListener lungo(final Blocco b)
	{
		return new View.OnLongClickListener()
		{
			public boolean onLongClick(View p1)
			{
				new Descrizione(schermo(),b);
				return true;
			}
		};
	}
}
