import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { FORECAST_MODEL_ALGORITHM_OPTIONS, FORECAST_MODEL_CRITICALITY_OPTIONS } from './types';
import type { ForecastModelFilter } from './types';

interface Props {
  value: ForecastModelFilter;
  onChange: (next: ForecastModelFilter) => void;
  onReset: () => void;
}

export function ForecastModelFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ForecastModelFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Forecast Model filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Forecast Model"
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
      <FilterField label="Model Name">
        <Input
          aria-label="Model Name filter"
          value={value.modelName ?? ''}
          onChange={(v) => patch({ modelName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Algorithm">
        <MultiSelect
          ariaLabel="Algorithm filter"
          options={FORECAST_MODEL_ALGORITHM_OPTIONS}
          value={value.algorithm ?? []}
          onChange={(v) => patch({ algorithm: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Trained On">
        <DateRange
          ariaLabel="Trained On"
          from={value.trainedOnFrom}
          to={value.trainedOnTo}
          onChange={(from, to) => patch({ trainedOnFrom: from, trainedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Mape">
        <RangeInputs
          ariaLabel="Mape"
          min={value.mapeMin}
          max={value.mapeMax}
          onChange={(min, max) => patch({ mapeMin: min, mapeMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={FORECAST_MODEL_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
