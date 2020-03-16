package com.schwarz.kokaindi

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.schwarz.kokaindi.observer.RefreshingReadonlyProperty


inline fun <reified T : ComponentActivity, reified V : Any> ComponentActivity.inject(
): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(this)
   return RefreshingReadonlyProperty<T, V> { t:T, desc ->
        KokainInstance.mInstance!!.refreshActivityContext(this)
       return@RefreshingReadonlyProperty KokainInstance.mInstance!!.create(t,V::class)
    }
}

inline fun <reified T : Fragment, reified V:Any> Fragment.inject(
): RefreshingReadonlyProperty<T, V> {
    KokainInstance.mInstance!!.refreshActivityContext(activity!!)
    return RefreshingReadonlyProperty<T, V>{ t:T, desc ->
        KokainInstance.mInstance!!.refreshActivityContext(activity!!)
        return@RefreshingReadonlyProperty KokainInstance.mInstance!!.create(t,V::class)
    }
}

inline fun <reified T : Any, reified V:Any> inject(
): RefreshingReadonlyProperty<T, V> {
    return RefreshingReadonlyProperty<T, V> { t:T, desc ->
        return@RefreshingReadonlyProperty KokainInstance.mInstance!!.create(t, V::class)
    }
}

inline fun Any.context(
): ActivityContextGuard {
    return KokainInstance.mInstance!!.mGuard
}



