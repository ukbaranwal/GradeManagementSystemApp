package com.iiitkalyani.grademanagementsystem;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
public class StudentGradesAdapter extends RecyclerView.Adapter<StudentGradesAdapter.ViewHolder> {

        private List<com.iiitkalyani.grademanagementsystem.StudentGrades> category_modelList;

        public StudentGradesAdapter(List<com.iiitkalyani.grademanagementsystem.StudentGrades> category_modelList) {
            this.category_modelList = category_modelList;
        }

        @NonNull
        @Override
        public StudentGradesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_row, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentGradesAdapter.ViewHolder viewHolder, int position) {
            String subjectCode = category_modelList.get(position).getSubjectCode();
            String grade = category_modelList.get(position).getGrade();
            viewHolder.setGrade(subjectCode, position, grade);
        }

        @Override
        public int getItemCount() {
            return category_modelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView subjectCode, grade;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                subjectCode = itemView.findViewById(R.id.subject_code);
                grade = itemView.findViewById(R.id.subject_grade);
            }

            private void setGrade(final String code, final int position, final String subjectgrade) {
                subjectCode.setText(code);
                grade.setText(subjectgrade);
            }
        }
    }
