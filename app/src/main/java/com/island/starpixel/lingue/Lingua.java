package com.island.starpixel.lingue;
import java.util.*;
import android.widget.*;
import com.island.starpixel.blocchi.*;
public abstract class Lingua
{
	public static Lingua lingua()
	{
		if(Locale.getDefault().equals(Locale.ITALY))return new Italiano();
		else return new Inglese();
	}
	public abstract String gioca()
	public abstract String aiuto()
	public abstract String descrizione()
	public abstract String nome()
	public abstract String seme()
	public abstract String creativa()
	public abstract String soppravivenza()
	public abstract String crea()
	public abstract String nome(Blocco b)
	public abstract String descrizione(Blocco b)
	public abstract String terreno()
	public abstract String nuovoUniverso()
	public abstract String teletrasportaGiu()
	public abstract String teletrasportaSu()
	public abstract String scegliTeletrasporto()
	public abstract String scegliPunto()
	public abstract String cancellando()
	public abstract String copiando()
	public abstract String unisciti()
	public abstract String ip()
	public abstract String selezionaArma()
	public abstract String rotea()
	public abstract String copia()
	public abstract String inventario()
	public abstract String costruisci()
	public abstract String scudi()
	public abstract String toccaALungo()
	public String gioca=gioca();
	public String aiuto=aiuto();
	public String descrizione=descrizione();
	public String nome=nome();
	public String seme=seme();
	public String creativa=creativa();
	public String soppravivenza=soppravivenza();
	public String crea=crea();
	public String terreno=terreno();
	public String nuovoUniverso=nuovoUniverso();
	public String teletrasportaGiu=teletrasportaGiu();
	public String teletrasportaSu=teletrasportaSu();
	public String copiando=copiando();
	public String cancellando=cancellando();
	public String scegliPunto=scegliPunto();
	public String scegliTeletrasporto=scegliTeletrasporto();
	public String ip=ip();
	public String unisciti=unisciti();
	public String rotea=rotea();
	public String selezionaArma=selezionaArma();
	public String copia=copia();
	public String inventario=inventario();
	public String costruisci=costruisci();
	public String scudi=scudi();
	public String toccaALungo=toccaALungo();
}
