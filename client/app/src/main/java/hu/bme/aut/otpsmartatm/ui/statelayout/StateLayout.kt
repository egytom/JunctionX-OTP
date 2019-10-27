package hu.bme.aut.otpsmartatm.ui.statelayout

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import hu.bme.aut.otpsmartatm.R
import android.content.Context

class StateLayout : LinearLayout {
    private var inflater: LayoutInflater? = null
    var errorLayout: View? = null
    var emptyLayout: View? = null
    var loadingLayout: View? = null
    var contentLayout: View? = null
    var stateListener: StateListener? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        inflater = LayoutInflater.from(context)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
            try {
                val errorLayoutReference = a.getResourceId(R.styleable.StateLayout_errorLayout, 0)

                if (errorLayoutReference != 0) {
                    errorLayout = inflater!!.inflate(errorLayoutReference, this, false)
                }


                val emptyLayoutReference = a.getResourceId(R.styleable.StateLayout_emptyLayout, 0)

                if (emptyLayoutReference != 0) {
                    emptyLayout = inflater!!.inflate(emptyLayoutReference, this, false)
                }

                val loadingLayoutReference =
                    a.getResourceId(R.styleable.StateLayout_loadingLayout, 0)

                if (loadingLayoutReference != 0) {
                    loadingLayout = inflater!!.inflate(loadingLayoutReference, this, false)
                }


            } finally {
                a.recycle()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 1)
            throw IllegalStateException("StateLayout must have just one child!")
        contentLayout = getChildAt(0)
        if (errorLayout != null) {
            addView(errorLayout)
        }
        if (emptyLayout != null) {
            addView(emptyLayout)
        }

        if (loadingLayout != null) {
            addView(loadingLayout)
        }
        state = LayoutState.Content
    }

    var state: LayoutState? = null
        set(value) {
            field = value

            contentLayout?.setVisibility(View.GONE)
            errorLayout?.setVisibility(View.GONE)
            emptyLayout?.setVisibility(View.GONE)
            loadingLayout?.setVisibility(View.GONE)

            when (value) {
                LayoutState.Loading -> {
                    if (loadingLayout == null) {
                        throw IllegalStateException("Loading layout is undedined!")
                    }
                    loadingLayout?.setVisibility(View.VISIBLE)
                }

                LayoutState.Error -> {
                    if (errorLayout == null) {
                        throw IllegalStateException("Error layout is undedined!")
                    }
                    errorLayout?.setVisibility(View.VISIBLE)
                }

                LayoutState.Empty -> {
                    if (emptyLayout == null) {
                        throw IllegalStateException("Empty layout is undedined!")
                    }
                    emptyLayout?.setVisibility(View.VISIBLE)
                }

                LayoutState.Content -> contentLayout?.setVisibility(View.VISIBLE)
            }

            stateListener?.onStateChanged(value!!)

        }
}
