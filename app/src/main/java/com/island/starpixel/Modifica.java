package com.island.starpixel;
import android.graphics.*;
import android.view.*;
import com.island.*;
import java.io.*;
public class Modifica extends Finestra
{
	private Testo nome;
	private Bottone selezionato;
	private String cartella;
	private Menu menu;
	Modifica(Menu s,String cartella)
	{
		super(s.schermo());
		menu=s;
		this.cartella=cartella;
		Gruppo g=new Gruppo(this,10,11);
		new Testo(g,1,1,9,2,schermo().lingua.nome);
		nome=new Testo(g,1,2,9,3,Memoria.leggi(cartella+schermo().blocchi.nome),Color.BLACK,Color.RED);
		nome.setOnClickListener(scrivi());
		new Testo(g,1,4,9,5,schermo().lingua.seme);
		new Testo(g,1,5,9,6,Memoria.leggi(cartella+schermo().blocchi.seme),Color.BLACK,Color.GREEN);
		selezionato=new Bottone(g,1,7,4,8,Color.DKGRAY,Color.LTGRAY);
		selezionato.scrivi(schermo().lingua.creativa).larghezzaX(0.6).setOnClickListener(modalita());
		Bottone b=new Bottone(g,6,7,9,8);
		b.scrivi(schermo().lingua.soppravivenza).larghezzaX(0.6).setOnClickListener(modalita());
		if(Lista.uguali(Memoria.leggi(cartella+schermo().blocchi.modalita),"false"))
		{
			selezionato.colore(Color.LTGRAY,Color.DKGRAY);
			selezionato=b;
			selezionato.colore(Color.DKGRAY,Color.LTGRAY);
		}
		new Bottone(g,1,9,9,10).scrivi(schermo().lingua.copia).setOnClickListener(copia());
		g.aggiorna();
	}
	private View.OnClickListener scrivi()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				final Testo t=(Testo)p1;
				Tastiera tas=new Tastiera(schermo(),t.colore(),t.testo())
				{
					protected void conferma(StringBuilder testo)
					{
						t.scrivi(testo);
					}
				};
				if(t.colore()==Color.GREEN)tas.versioneNumerica();
			}
		};
	}
	private View.OnClickListener modalita()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				selezionato.colore(Color.LTGRAY,Color.DKGRAY);
				selezionato=(Bottone)p1;
				selezionato.colore(Color.DKGRAY,Color.LTGRAY);
			}
		};
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	public void onBackPressed()
	{
		Memoria.salva(cartella+schermo().blocchi.nome,nome.testo().toString());
		Memoria.salva(cartella+schermo().blocchi.modalita,String.valueOf(selezionato.testo().equals(schermo().lingua.creativa)));
		super.onBackPressed();
	}
	private View.OnClickListener copia()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				final Caricamento c=new Caricamento(schermo(),schermo().lingua.copiando);
				schermo().thread().post(new Runnable()
					{
						public void run()
						{
							copia(cartella,Memoria.casuale(Memoria.sopra(cartella))+File.separator,c,100);
							c.carica(100);
							schermo().runOnUiThread(new Runnable()
							{
								public void run()
								{
									menu.inizializza();
								}
							});
							cancel();
						}
					});
			}
		};
	}
	public void copia(String dove,String arrivo,Caricamento c,double parte)
	{
		String[]files=Memoria.file(dove);
		for(String string:files)
		{
			String nuovo=dove+string;
			if(Memoria.directory(nuovo))copia(nuovo+File.separator,arrivo+string+File.separator,c,parte/files.length);
			else 
			{
				Memoria.salva(arrivo+string,Memoria.leggi(nuovo));
				c.carica(parte/files.length);
				Memoria.salva(arrivo+string,Memoria.leggi(dove+string));
			}
		}
	}
}
