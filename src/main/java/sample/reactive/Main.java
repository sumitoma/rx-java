package sample.reactive;


import io.reactivex.*;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.ResourceObserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws Exception{
        //createObservables();

        //createColdObservable();

        //createHotObservable();

        //throwException();

        //throwExceptionFromCallable();

        //createEmptyObservable();

        //createNeverObservable();

        //createObservableUsingRange();

        //createObservableUsingDefer();

        //createObservableUsingCallable();

        //createColdObservableUsingInterval();

        //createHotObservableUsingInterval();

        //special type of observable with unique quality or characteristics
        //createSingle();

        //createMaybe();

        //createCompletable();

        //handleDisposable();
        //handleDisposableInObserver();
        //handleDisposableOutsideObserver();
        //compositeDispoableOutsideObserver();

        //Operators(Observable)
        //mapOperator();
        //filterOperator();
        //combineMapAndFilter();
        //takeOperator();
        //takeWithInterval();
        //takeWhileOperator();
        //skipOperator();
        //skipWhileOperator();
        //distinct();
        //distinctWithKeySelector();
        //distinctUntilChanged();
        //distinctUntilChangedWithSelector();
        //defaultIfEmpty();
        //switchIfEmpty();

        //useRepeat();
        //useScan();
        //useScanWithInitial();
        //useSorted();
        //useSortedNonComparable();
        //delayedObservable();
        //delyaedError();
        //containsPrimitive();
        //containsObject();
        //doOnError();
        //onErrorResumeNext();
        //onErrorReturn();
        //onErrorReturnItem();

        //retryWithPredicate();
        //retry();
        //retryUntil();

        //doOnSubscribe();
        //doOnNext();
        //doOnComplete();

        //doFinally();
        //doOnDispose();

        //merge();
        //mergeWith();
        //mergeWithArray();
        //mergeWithIterable();
        //mergeInfinite();

        //zip();
        //zipWith();
        //zipIterable();

        //flatMap();
        //flatMapBifuntion();

        //concat();
        //emission order matters(compare to merge)
        //concantWith();
        concatMap();

    }

    private static void concat() {
        Observable<Integer> oneToFive = Observable.range(1, 5);
        Observable<Integer> sixToTen = Observable.range(6, 5);

        Observable.concat(oneToFive, sixToTen).subscribe(System.out::println);
    }

    private static void concantWith() throws InterruptedException {
//        Observable<Integer> oneToFive = Observable.range(1, 5);
//        Observable<Integer> sixToTen = Observable.range(6, 5);
//
//        oneToFive.concatWith(sixToTen).subscribe(System.out::println);
        Observable<String> interval = Observable.interval(1, TimeUnit.SECONDS)
                .take(3)
                .map(item -> "interval : "+item);
        Observable<String> interval1 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(item -> "interval1 "+item);

        interval.concatWith(interval1).subscribe(System.out::println);

        Thread.sleep(6000);

    }

    private static void concatMap() {
        Observable.just("foo", "bar", "jam")
                .concatMap(item -> Observable.fromArray(item.split("")))
                .subscribe(System.out::println);

    }

    private static void flatMapBifuntion() {
        Observable.just("foo", "bar", "jam").flatMap(string -> {
            return Observable.fromArray(string.split(""));
        }, (actual, second)->{
            return actual +" "+ second;
        }).subscribe(System.out::println);
    }

    private static void flatMap() {
        Observable.just("foo", "bar", "jam").flatMap(string -> {
         return Observable.fromArray(string.split(""));
        }).subscribe(System.out::println);
    }

    private static void zip() {
        Observable<Integer> oneToFive = Observable.range(1, 5);
        Observable<Integer> sixToTen = Observable.range(6, 5);
        Observable<Integer> elevenToFifteen = Observable.fromIterable(Arrays.asList(1,2,3,4,5));
        Observable.zip(oneToFive, sixToTen, elevenToFifteen, (a,b,c)-> a + b +c).subscribe(System.out::println);

    }

    private static void zipWith() {
        Observable<Integer> oneToFive = Observable.range(1, 5);
        Observable<Integer> sixToTen = Observable.range(6, 5);
        Observable<Integer> elevenToFifteen = Observable.fromIterable(Arrays.asList(1,2,3,4,5));

        oneToFive.zipWith(sixToTen, (a,b)-> a+b).zipWith(elevenToFifteen, (a, b)-> a+b).subscribe(System.out::println);
    }

    private static void zipIterable() {
        Observable<Integer> oneToFive = Observable.range(1, 5);
        Observable<Integer> sixToTen = Observable.range(6, 5);
        Observable<Integer> elevenToFifteen = Observable.fromIterable(Arrays.asList(1,2,3,4,5,6));

        List<Observable<Integer>> list = Arrays.asList(oneToFive, sixToTen, elevenToFifteen);

        Observable.zipIterable(list, Arrays::toString, true, 1)
        .subscribe(System.out::println);
    }

    private static void mergeInfinite() throws InterruptedException {
        Observable<String> interval1  = Observable.interval(1, TimeUnit.SECONDS)
                .map(item -> "From infinite1: "+ item);
        Observable interval2 = Observable.interval(2, TimeUnit.SECONDS)
                .map(item -> "From infinite2: "+item);

        interval1.mergeWith(interval2).subscribe(System.out::println);

        Thread.sleep(6000);

    }

    private static void mergeWith() {
        Observable<Integer> observable = Observable.range(1, 3);
        Observable<Integer> observable1 = Observable.range(4, 3);
        Observable<Integer> observabl2 = Observable.range(7, 4);
        observable.mergeWith(observable1).mergeWith(observabl2).subscribe(System.out::println);
    }

    private static void merge() {
        Observable<Integer> observable = Observable.range(1, 10);
        Observable<Integer> observable1 = Observable.range(1, 10);
        Observable<Integer> observabl2 = Observable.range(1, 10);
        Observable.merge(observable, observable1, observabl2).subscribe(System.out::println);
    }

    private static void mergeWithArray() {
        Observable<Integer> observable = Observable.range(1, 3);
        Observable<Integer> observable1 = Observable.range(4, 3);
        Observable<Integer> observabl2 = Observable.range(7, 4);
        Observable<Integer> observabl3 = Observable.range(7, 4);
        Observable<Integer> observabl4 = Observable.range(7, 4);
        Observable<Integer> observabl5 = Observable.range(7, 4);
        Observable.mergeArray(observable, observable1, observabl2, observabl3, observabl4, observabl5, observable).subscribe(System.out::println);
    }

    private static void mergeWithIterable() {
        Observable<Integer> observable = Observable.range(1, 3);
        Observable<Integer> observable1 = Observable.range(4, 3);
        Observable<Integer> observabl2 = Observable.range(7, 4);
        Observable<Integer> observabl3 = Observable.range(7, 4);
        Observable<Integer> observabl4 = Observable.range(7, 4);
        Observable<Integer> observabl5 = Observable.range(7, 4);
        List<Observable<Integer>> list = Arrays.asList(observable, observable1, observabl2, observabl3, observabl4, observabl5);

        Observable.merge(list).subscribe(System.out::println);
    }

    private static void doFinally() {
        Observable.just(1,2,3,4)
                .doFinally(()-> System.out.println("doFinally"))
                .subscribe(System.out::println);
    }

    private static void doOnDispose() {
        Disposable d = Observable.just(1,2,3,4)
                .doOnDispose(() -> System.out.println("disposed"))
                .doOnSubscribe(disposable -> { disposable.dispose();})
                .subscribe(System.out::println);

    }

    private static void doOnSubscribe() {
        Observable.just(1,2,3,4)
                .doOnSubscribe(disposable -> System.out.println("subscribed"))
                .subscribe(System.out::println);
    }

    private static void doOnNext() {
        Observable.just(1,2,3,4)
                .doOnNext(item -> System.out.println("onNext "+item))
                .subscribe(System.out::println);
    }

    private static void doOnComplete() {
        Observable.just(1,2,3,4)
                .doOnComplete(() -> System.out.println("completed"))
                .subscribe(System.out::println);
    }

    private static void retry() {
        Observable.error(new Exception("New Exception"))
                .retry(3).subscribe(System.out::println, System.out::println);
    }

    private static void retryUntil() {
        AtomicInteger atomicInteger = new AtomicInteger();
        Observable.error(new Exception("New Exception"))
                .doOnError(error -> {
                    System.out.println(atomicInteger.get());
                    atomicInteger.getAndIncrement();
                })
                .retryUntil(()-> {
                    System.out.println("retry");
                    return atomicInteger.get() >= 3;
                }).subscribe(System.out::println, System.out::println);
    }

    private static void retryWithPredicate() {
        Observable.error(new Exception("New Exception"))
                .retry(error-> error instanceof Exception).subscribe(System.out::println, System.out::println);
    }

    private static void onErrorReturnItem() {
        Observable.error(new Exception("New Exception"))
            .onErrorReturnItem("sumit on error return item").subscribe(System.out::println, System.out::println);

    }

    private static void doOnError() {
        Observable.error(new Exception("New Exception"))
                .doOnError(error-> System.out.println(error)).subscribe(System.out::println, System.out::println);
    }

    private static void onErrorResumeNext() {
        Observable.error(new Exception("New Exception"))
                .onErrorResumeNext(Observable.just(1,2,3,4,5)).subscribe(System.out::println, System.out::println);
    }

    private static void onErrorReturn() {
        Observable.error(new Exception("New Exception"))
                .onErrorReturn(error-> error.hashCode()).subscribe(System.out::println, System.out::println);
    }

    static private class User{
        String name;

        private User(String name){
            this.name = name;
        }
    }

    private static void containsObject() {
        User user = new User("sumit");
        Observable.just(user).contains(new User("sumit")).subscribe(System.out::println);
    }

    private static void containsPrimitive() {
        Observable.just(1,2,3,4,5)
                .contains(6).subscribe(System.out::println);
    }

    private static void delyaedError() throws InterruptedException {

            Observable.error(new Exception("New Exception"))
                    .delay(3000, TimeUnit.MILLISECONDS)
                    .subscribe(System.out::println, System.out::println);

            Thread.sleep(5000);

    }

    private static void delayedObservable() throws InterruptedException {
        Observable.just(1,2,3,4,5)
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        Thread.sleep(5000);
    }

    private static void useSortedNonComparable() {
        Observable.just("foo", "nume", "a").sorted((first, second)-> Integer.compare(first.length(), second.length())).subscribe(System.out::println);
    }


    private static void useSorted() {
        Observable.just(1,2,3,4,5,5,6,7).sorted(Comparator.reverseOrder()).subscribe(System.out::println);
    }

    private static void useScan() {
        Observable.just(1,2,3,4,5,5,6,7).scan((accumulator, next)-> {
            System.out.println(accumulator+" + "+ next);
            return accumulator + next;
        }).subscribe(System.out::println);
    }

    private static void useRepeat() {
        Observable.just(1,2,3,4,5,5,6,7).repeat(2).subscribe(System.out::println);
    }

    private static void useScanWithInitial() {
        Observable.just(1,2,3,4,5,5,6,7).scan(10, (accumulator, next)-> {
            System.out.println(accumulator+" + "+ next);
            return accumulator + next;
        }).subscribe(System.out::println);

    }

    private static void defaultIfEmpty() {
        Observable.just(1,2,3,4,5,5,6,7).filter(item-> item > 12).defaultIfEmpty(100).subscribe(System.out::println);

    }

    private static void switchIfEmpty() {
        Observable.just(1,2,3,4,5,5,6,7).filter(item -> item > 12).switchIfEmpty(Observable.just(1,2,3,4)).subscribe(System.out::println);
    }

    private static void distinctUntilChangedWithSelector() {
        Observable.just("from", "from", "nan", "nano", "nan", "jai").distinctUntilChanged(String::length).subscribe(System.out::println);
    }

    private static void distinctUntilChanged() {
        Observable.just("from", "from", "nan", "nano", "nan", "jai").distinctUntilChanged().subscribe(System.out::println);
    }

    private static void distinctWithKeySelector() {
        Observable.just("from","nan","nano").distinct(String::length).subscribe(System.out::println);
    }

    private static void distinct() {
        Observable.just(1,2,3,4,5,5,6,7).distinct().subscribe(System.out::println);
    }

    private static void skipWhileOperator() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6,1,2,3);
        observable.skipWhile(item-> item <= 3).subscribe(System.out::println);
    }

    private static void skipOperator() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6,1,2,3);
        observable.skip(4).subscribe(System.out::println);
    }

    private static void takeWhileOperator() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6,1,2,3);
        observable.takeWhile(item-> item <= 3).subscribe(System.out::println);
    }

    private static void takeWithInterval() throws  Exception{
        Observable<Long> observable = Observable.interval(300, TimeUnit.MILLISECONDS);
        observable.take(6, TimeUnit.SECONDS).subscribe(System.out::println);
        Thread.sleep(4000);
    }

    private static void takeOperator() {
        Observable<Integer> observable = Observable.just(1,2,3,4,5);
        observable.take(2).subscribe(System.out::println);
    }

    private static void combineMapAndFilter() {
        Observable<Integer> observable = Observable.just(1,2,3,4,5);
        observable.filter(item -> item%2==0).map(item-> item*2).subscribe(System.out::println);
    }

    private static void filterOperator() {
        Observable<Integer> observable = Observable.just(1, 2,3 ,4);

        observable.filter(item -> {
            if(item > 2)
                return true;
            return false;
        }).subscribe(System.out::println);
    }

    private static void mapOperator() {
        Observable<Integer> observable = Observable.just(1, 2,3 ,4);

        observable.map(item -> item*2).subscribe(System.out::println);
    }

    private static void compositeDispoableOutsideObserver() throws Exception{

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = observable.subscribe(item -> System.out.println("Observer 1 "+ item));
        Disposable disposable1 = observable.subscribe(item -> System.out.println("Observer 2 "+ item));
        compositeDisposable.addAll(disposable, disposable1);
        Thread.sleep(3000);
        compositeDisposable.delete(disposable);
        compositeDisposable.dispose();
        Thread.sleep(3000);


    }

    private static void handleDisposableOutsideObserver() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        ResourceObserver observer =  new ResourceObserver<Integer>() {
            //Disposable disposable;
            @Override
            public void onNext(Integer integer) {
                //if(integer == 3)
                    //disposable.dispose();
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                //disposable.dispose();
            }
        };

       Disposable disposable = observable.subscribeWith(observer);
       disposable.dispose();
    }

    private static void handleDisposableInObserver() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        observable.subscribe(new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("on subscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                if(integer == 3)
                    disposable.dispose();
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                disposable.dispose();
            }
        });
    }

    private static void handleDisposable() throws Exception{
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = observable.subscribe(System.out::println);
        Thread.sleep(3000);
        disposable.dispose();
        Thread.sleep(3000);
    }

    private static void createCompletable() {
        Completable.fromSingle(Single.just("Hello Worled")).subscribe(()-> System.out.println("Done"));
    }

    private static void createMaybe() {
        Maybe.empty().subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("on Subscribe");
            }

            @Override
            public void onSuccess(Object o) {
                System.out.println("On Success");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
    }

    private static void createSingle() {
        Single.just("Hello Worlds").subscribe(System.out::println);
    }

    private static void createHotObservableUsingInterval() throws Exception{
        ConnectableObservable observable = Observable.interval(1, TimeUnit.SECONDS).publish();

        observable.subscribe((item)->System.out.println("Observer 1: "+item));
        observable.connect();

        Thread.sleep(3000);

        observable.subscribe((item)->System.out.println("Observer 2: "+item));

        Thread.sleep(4000);

    }

    private static void createColdObservableUsingInterval() throws Exception{

        Observable observable = Observable.interval(1, TimeUnit.SECONDS);

        observable.subscribe((item)->System.out.println("Observer 1: "+item));

        Thread.sleep(3000);

        observable.subscribe((item)->System.out.println("Observer 2: "+item));

        Thread.sleep(4000);

    }

    private static void createObservableUsingCallable() {
        Observable<Integer> observable= Observable.fromCallable(()->{
            System.out.println("Calling getNumber");
            return getNumber();
        });

        observable.subscribe(System.out::println, System.out::println, ()-> System.out.println("Completed"), System.out::println);

    }

    private static int getNumber() {
        System.out.println("Generating number");
        return 1/0;
    }



    static int start=1,count=2;
    private static void createObservableUsingDefer() {

        Observable<Integer> observable = Observable.defer(()-> {
            System.out.println("New Observable is created with end " + count);
            return Observable.range(start, count);
        });

        observable.subscribe(System.out::println);

        count=4;
        observable.subscribe(System.out::println);
    }

    private static void createObservableUsingRange() {

        Observable<Integer> observable = Observable.range(1, 10);

        observable.subscribe(System.out::println);
    }


    private static void createEmptyObservable() {

        Observable observable = Observable.empty();

        observable.subscribe(System.out::println, System.out::println, ()-> System.out.println("Completed"), System.out::println);
    }

    private static void createNeverObservable() throws Exception {

        Observable observable = Observable.never();

        observable.subscribe(System.out::println, System.out::println, ()-> System.out.println("Completed"));

        Thread.sleep(3000);
    }

    private static void throwException() {

        Observable<Integer> observable = Observable.error(new Exception());

        observable.subscribe(System.out::println, (error)->{ System.out.println("observer 1: "+ error.hashCode());});

        observable.subscribe(System.out::println, (error)->{ System.out.println("observer 2: "+ error.hashCode());});
    }

    private static void throwExceptionFromCallable() {

        Observable observable = Observable.error(() -> {
            System.out.println("New Exception Created!!");
            return new Exception("An Exception");
        });

        observable.subscribe(System.out::println, (error)->{ System.out.println("observer 1: "+ error.hashCode());});

        observable.subscribe(System.out::println, (error)->{ System.out.println("observer 2: "+ error.hashCode());});
    }

    private static void createHotObservable() {
        ConnectableObservable<Integer> observable = Observable.just(1,2,3,4,5).publish();

        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);

        observable.connect();

        observable.subscribe(System.out::println);
    }

    private static void createObservables() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        observable.subscribe(System.out::println);

        Observable<Integer> observable1 = Observable.fromIterable(Arrays.asList(1, 2, 3));

        observable1.subscribe(System.out::println);

        Observable<Integer> observable2 = Observable.create(emitter -> {
           emitter.onNext(1);
           emitter.onNext(2);
           emitter.onNext(3);
           emitter.onNext(null);

           emitter.onComplete();
        });

        observable2.subscribe(System.out::println, System.out::println, ()->{
            System.out.println("Completed");
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("subscribed");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed!!");
            }
        };

        observable2.subscribe(observer);
    }

    private static void createColdObservable() throws Exception{

        Observable<Integer> observable = Observable.just(1,2,3,4,5,6);

        observable.subscribe(System.out::println);

        Thread.sleep(3000);

        observable.subscribe(System.out::println);

    }


}
