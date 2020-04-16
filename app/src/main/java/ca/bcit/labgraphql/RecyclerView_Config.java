package ca.bcit.labgraphql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private StudentsAdapter mStudentsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context,
                          List<Student> students, List<String> keys) {
        mContext = context;
        mStudentsAdapter = new StudentsAdapter(students, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mStudentsAdapter);
    }

    class StudentItemView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mNum;
        private TextView mSet;


        private String key;

        public StudentItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.student_list_item, parent, false));

            mName = itemView.findViewById(R.id.name_txtView);
            mNum = itemView.findViewById(R.id.num_txtView);
            mSet = itemView.findViewById(R.id.set_txtView);
        }

        public void bind(Student student, String key) {
            mName.setText(student.getStudentName());
            mNum.setText(student.getStudentNum());
            mSet.setText(student.getStudentSet());
            this.key = key;
        }

    }

    class StudentsAdapter extends RecyclerView.Adapter<StudentItemView>{
        private List<Student> mStudentList;
        private List<String> mKeys;

        public StudentsAdapter(List<Student> mStudentList, List<String> mKeys) {
            this.mStudentList = mStudentList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public StudentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StudentItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentItemView holder, int position) {
            holder.bind(mStudentList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mStudentList.size();
        }
    }

}
