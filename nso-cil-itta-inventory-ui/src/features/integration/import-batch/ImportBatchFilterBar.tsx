import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { IMPORT_BATCH_BATCH_STATE_OPTIONS, IMPORT_BATCH_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ImportBatchFilter } from './types';

interface Props {
  value: ImportBatchFilter;
  onChange: (next: ImportBatchFilter) => void;
  onReset: () => void;
}

export function ImportBatchFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ImportBatchFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Import Batch filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Import Batch"
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
      <FilterField label="File Name">
        <Input
          aria-label="File Name filter"
          value={value.fileName ?? ''}
          onChange={(v) => patch({ fileName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Entity Type">
        <Input
          aria-label="Entity Type filter"
          value={value.entityType ?? ''}
          onChange={(v) => patch({ entityType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Total Rows">
        <RangeInputs
          ariaLabel="Total Rows"
          min={value.totalRowsMin}
          max={value.totalRowsMax}
          onChange={(min, max) => patch({ totalRowsMin: min, totalRowsMax: max })}
        />
      </FilterField>
      <FilterField label="Processed Rows">
        <RangeInputs
          ariaLabel="Processed Rows"
          min={value.processedRowsMin}
          max={value.processedRowsMax}
          onChange={(min, max) => patch({ processedRowsMin: min, processedRowsMax: max })}
        />
      </FilterField>
      <FilterField label="Failed Rows">
        <RangeInputs
          ariaLabel="Failed Rows"
          min={value.failedRowsMin}
          max={value.failedRowsMax}
          onChange={(min, max) => patch({ failedRowsMin: min, failedRowsMax: max })}
        />
      </FilterField>
      <FilterField label="Batch State">
        <MultiSelect
          ariaLabel="Batch State filter"
          options={IMPORT_BATCH_BATCH_STATE_OPTIONS}
          value={value.batchState ?? []}
          onChange={(v) => patch({ batchState: v.length ? v : undefined })}
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
          options={IMPORT_BATCH_LIFECYCLE_STAGE_OPTIONS}
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
