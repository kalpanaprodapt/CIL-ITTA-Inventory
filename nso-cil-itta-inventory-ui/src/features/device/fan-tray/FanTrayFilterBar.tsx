import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { FAN_TRAY_AIRFLOW_OPTIONS, FAN_TRAY_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { FanTrayFilter } from './types';

interface Props {
  value: FanTrayFilter;
  onChange: (next: FanTrayFilter) => void;
  onReset: () => void;
}

export function FanTrayFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: FanTrayFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Fan Tray filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Fan Tray"
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
      <FilterField label="Chassis Id">
        <Input
          aria-label="Chassis Id filter"
          placeholder="UUID"
          value={value.chassisId ?? ''}
          onChange={(v) => patch({ chassisId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Slot Number">
        <RangeInputs
          ariaLabel="Slot Number"
          min={value.slotNumberMin}
          max={value.slotNumberMax}
          onChange={(min, max) => patch({ slotNumberMin: min, slotNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Fan Count">
        <RangeInputs
          ariaLabel="Fan Count"
          min={value.fanCountMin}
          max={value.fanCountMax}
          onChange={(min, max) => patch({ fanCountMin: min, fanCountMax: max })}
        />
      </FilterField>
      <FilterField label="Airflow">
        <MultiSelect
          ariaLabel="Airflow filter"
          options={FAN_TRAY_AIRFLOW_OPTIONS}
          value={value.airflow ?? []}
          onChange={(v) => patch({ airflow: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
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
          options={FAN_TRAY_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
