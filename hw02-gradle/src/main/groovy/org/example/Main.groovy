package org.example

// набор пачек денег всех возможных номиналов
class MoneyPacks {

    final List nominals = [5000, 1000, 500, 100]
    List moneyPacks = []

    // пачка денег одного номинала
    class MoneyPack {
        Integer nominal
        Integer amount
        // конструктор
        def MoneyPack (int nominal, int amount) {
            if (!nominals.contains(nominal))
                throw new Exception ("Ошибка: Нет такого номинала \"$nominal\"! ")
            this.nominal = nominal
            this.amount = amount
        }
        // сумма денег в пачке
        Integer sum () {
            nominal*amount
        }
        // печать инфо
        String toString() {
            amount ? "[$nominal] x $amount" : null
        }
    }
    // конструктор пустого набора
    def MoneyPacks () {
        nominals.each {
            moneyPacks.add (new MoneyPack(it, 0))
        }
        this
    }

    // конструктор набора с заданными значениями
    def MoneyPacks (Map packs) {
        packs.each() {nominal, amount ->
            moneyPacks.add (new MoneyPack(nominal, amount))
        }
        (nominals - packs.keySet()).each {
            moneyPacks.add (new MoneyPack(it, 0))
        }
    }

    // конструктор - копия
    def MoneyPacks (MoneyPacks fromPack) {
        fromPack.moneyPacks.each() {mp ->
            moneyPacks.add (new MoneyPack(mp.nominal, mp.amount))
        }
    }

    // добавление пачки
    MoneyPacks plus (MoneyPack pack) {
        moneyPacks.find {it.nominal == pack.nominal}.amount += pack.amount
        this
    }

    // добавление набора пачек
    MoneyPacks plus (MoneyPacks mnPack) {
        def resPacks = new MoneyPacks (this)
        mnPack.moneyPacks.each {mp->
            resPacks.moneyPacks.find {it.nominal == mp.nominal}.amount += mp.amount
        }
        resPacks
    }

    // вычитание набора пачек
    MoneyPacks minus (MoneyPacks mnPack) {
        def resPacks = new MoneyPacks (this)
        mnPack.moneyPacks.each {mp->
            def currentPack = resPacks.moneyPacks.find {it.nominal == mp.nominal}
            if (currentPack.amount >= mp.amount)
                currentPack.amount -= mp.amount
            else
                throw new Exception ("Ошибка: недостаточно купюр номиналом $mp.nominal")
        }
        resPacks
    }

    Integer sum () {
        moneyPacks*.sum().sum()
    }

    String toString() {
        moneyPacks*.toString().findAll().join("\r\n") + "\r\n= ${this.sum()}"
    }

    MoneyPacks rightShift (Integer value) {
        def rest = value
        def resPacks = new MoneyPacks()
        this.moneyPacks.each {mp ->
            def nominal = mp.nominal
            def n = rest.intdiv (nominal)
            if (mp.amount <= n)
                n = mp.amount
            resPacks += new MoneyPack(nominal, n)
            rest -= nominal*n
        }
        if (rest)
            return null
        else
            return resPacks
    }
}

class ATM {
    MoneyPacks moneyPacks
    def ATM () {
        moneyPacks = new MoneyPacks()
    }
    def getCurrentMoney () {
        moneyPacks.sum()
    }
    def leftShift (MoneyPacks newMoneyPacks) {
        moneyPacks += newMoneyPacks
    }
    MoneyPacks rightShift (int value) {
        def resPacks = moneyPacks >> value
        if (!resPacks)
            throw new Exception ("Ошибка: Невозможно выдать запрошенную сумму")
        else {
            moneyPacks -= resPacks
            return resPacks
        }
    }
}

// тесты

def mp = new MoneyPacks ([5000:3,1000:1,500:5])
assert mp.sum() == 18500
def mp2 = new MoneyPacks ([5000:2,500:5])
assert mp2.sum() == 12500
assert (mp+mp2).sum() == 31000
assert (mp-mp2).sum() == 6000

def atm = new ATM ()
atm << mp2
assert atm.getCurrentMoney() == 12500
atm << mp
assert atm.getCurrentMoney() == 31000
def mp3 = atm >> 7000
assert mp3.sum() == 7000
assert atm.getCurrentMoney() == 24000