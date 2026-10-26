import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ALARM_INSTANCE_ALARM_STATE_OPTIONS, ALARM_INSTANCE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { AlarmInstanceFilter } from './types';

interface Props {
  value: AlarmInstanceFilter;
  onChange: (next: AlarmInstanceFilter) => void;
  onReset: () => void;
}

export function AlarmInstanceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AlarmInstanceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Alarm Instance filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Alarm Instance"
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
      <FilterField label="Definition Id">
        <Input
          aria-label="Definition Id filter"
          placeholder="UUID"
          value={value.definitionId ?? ''}
          onChange={(v) => patch({ definitionId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Source Ref">
        <Input
          aria-label="Source Ref filter"
          value={value.sourceRef ?? ''}
          onChange={(v) => patch({ sourceRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Raised At">
        <DateRange
          ariaLabel="Raised At"
          from={value.raisedAtFrom}
          to={value.raisedAtTo}
          onChange={(from, to) => patch({ raisedAtFrom: from, raisedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Cleared At">
        <DateRange
          ariaLabel="Cleared At"
          from={value.clearedAtFrom}
          to={value.clearedAtTo}
          onChange={(from, to) => patch({ clearedAtFrom: from, clearedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Acked By">
        <Input
          aria-label="Acked By filter"
          value={value.ackedBy ?? ''}
          onChange={(v) => patch({ ackedBy: v || undefined })}
        />
      </FilterField>
      <FilterField label="Alarm State">
        <MultiSelect
          ariaLabel="Alarm State filter"
          options={ALARM_INSTANCE_ALARM_STATE_OPTIONS}
          value={value.alarmState ?? []}
          onChange={(v) => patch({ alarmState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={ALARM_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
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
