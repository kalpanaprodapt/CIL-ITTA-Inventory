import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { FIELD_TASK_CRITICALITY_OPTIONS } from './types';
import type { FieldTaskFilter } from './types';

interface Props {
  value: FieldTaskFilter;
  onChange: (next: FieldTaskFilter) => void;
  onReset: () => void;
}

export function FieldTaskFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: FieldTaskFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Field Task filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Field Task"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Work Order Id">
        <Input
          aria-label="Work Order Id filter"
          placeholder="UUID"
          value={value.workOrderId ?? ''}
          onChange={(v) => patch({ workOrderId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Task Name">
        <Input
          aria-label="Task Name filter"
          value={value.taskName ?? ''}
          onChange={(v) => patch({ taskName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Sequence">
        <RangeInputs
          ariaLabel="Sequence"
          min={value.sequenceMin}
          max={value.sequenceMax}
          onChange={(min, max) => patch({ sequenceMin: min, sequenceMax: max })}
        />
      </FilterField>
      <FilterField label="Duration Minutes">
        <RangeInputs
          ariaLabel="Duration Minutes"
          min={value.durationMinutesMin}
          max={value.durationMinutesMax}
          onChange={(min, max) => patch({ durationMinutesMin: min, durationMinutesMax: max })}
        />
      </FilterField>
      <FilterField label="Completed On">
        <DateRange
          ariaLabel="Completed On"
          from={value.completedOnFrom}
          to={value.completedOnTo}
          onChange={(from, to) => patch({ completedOnFrom: from, completedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={FIELD_TASK_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
