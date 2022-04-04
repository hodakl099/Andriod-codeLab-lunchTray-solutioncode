
package com.example.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lunchtray.constants.ItemType
import com.example.lunchtray.data.DataSource
import java.text.NumberFormat

class OrderViewModel : ViewModel() {

    // Map of menu items
    val menuItems = DataSource.menuItems

    // Default values for item prices
    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    // Default tax rate
    private val taxRate = 0.08

    // Entree for the order
    private val _entree = MutableLiveData<MenuItem?>()
    val entree: LiveData<MenuItem?> = _entree

    // Side for the order
    private val _side = MutableLiveData<MenuItem?>()
    val side: LiveData<MenuItem?> = _side

    // Accompaniment for the order.
    private val _accompaniment = MutableLiveData<MenuItem?>()
    val accompaniment: LiveData<MenuItem?> = _accompaniment

    // Subtotal for the order
    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = Transformations.map(_subtotal) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Total cost of the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    /**
     * Set the entree for the order.
     */
    fun setEntree(entree: String) {
        // TODO: if _entree.value is not null, set the previous entree price to the current
        //  entree price.
        var filteredValuesMap = menuItems[entree]
        _entree.value = filteredValuesMap
        _entree.value?.let{
            previousEntreePrice = it.price
        }
        // TODO: if _subtotal.value is not null subtract the previous entree price from the current
        // TODO: set the current entree value to the menu item corresponding to the passed in string
        filteredValuesMap?.let {
            updateSubtotal(it.price)
        }
    }

    /**
     * Set the side for the order.
     */
    fun setSide(side: String) {
        // TODO: if _side.value is not null, set the previous side price to the current side price.
        var filterdValuesMapSide = menuItems[side]
        _side.value = filterdValuesMapSide
        _side.value?.let {
            previousSidePrice = it.price
        }
        // TODO: update the subtotal to reflect the price of the selected side.
        // TODO: set the current side value to the menu item corresponding to the passed in string
        filterdValuesMapSide?.let {
            updateSubtotal(it.price)
        }
    }

    /**
     * Set the accompaniment for the order.
     */
    fun setAccompaniment(accompaniment: String) {
        // TODO: if _accompaniment.value is not null, set the previous accompaniment price to the
        //  current accompaniment price.
        var filterdValuesMapAccomp = menuItems[accompaniment]
        _accompaniment.value = filterdValuesMapAccomp
        // TODO: if _accompaniment.value is not null subtract the previous accompaniment price from
        //  the current subtotal value. This ensures that we only charge for the currently selected
        //  accompaniment.
        _accompaniment.value?.let {
            previousAccompanimentPrice = it.price
        }
        // TODO: set the current accompaniment value to the menu item corresponding to the passed in
        //  string
        // TODO: update the subtotal to reflect the price of the selected accompaniment.
        filterdValuesMapAccomp?.let {
            updateSubtotal(it.price)
        }
    }

    /**
     * Update subtotal value.
     */
    private fun updateSubtotal(itemPrice: Double) {
        // TODO: if _subtotal.value is not null, update it to reflect the price of the recently
        //  added item.
        _subtotal.value?.let {
            _subtotal.value = it + itemPrice
        } ?: _subtotal.value.let {
            _subtotal.value = itemPrice
        }
        // TODO: calculate the tax and resulting total
        calculateTaxAndTotal()
    }

    /**
     * Calculate tax and update total.
     */
    fun calculateTaxAndTotal() {
        // TODO: set _tax.value based on the subtotal and the tax rate.
        var tarifa = 0.0
        _subtotal.value?.let { tarifa = it * taxRate}
        _tax.value =  tarifa
        // TODO: set the total based on the subtotal and _tax.value.
        _subtotal.value?.let { subtotal ->
            _tax.value?.let { tax ->
                _total.value = subtotal + tax
            }
        }
    }

    /**
     * Reset all values pertaining to the order.
     */
    fun resetOrder() {
        // TODO: Reset all values associated with an order
        _entree.value = null
        _side.value = null
        _accompaniment.value = null
        _subtotal.value = 0.0
        _total.value = 0.0
        _tax.value = 0.0








    }
}
