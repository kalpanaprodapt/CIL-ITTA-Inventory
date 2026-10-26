import type { Column } from './DataTable';

interface Props<T> {
  columns: Column<T>[];
  visible: string[];
  onChange: (visible: string[]) => void;
}

export function ColumnPicker<T>({ columns, visible, onChange }: Props<T>) {
  return (
    <details className="column-picker">
      <summary>Columns</summary>
      <ul>
        {columns.map((c) => (
          <li key={c.key}>
            <label>
              <input
                type="checkbox"
                checked={visible.includes(c.key)}
                onChange={() =>
                  onChange(visible.includes(c.key) ? visible.filter((k) => k !== c.key) : [...visible, c.key])
                }
              />
              {c.header}
            </label>
          </li>
        ))}
      </ul>
    </details>
  );
}
