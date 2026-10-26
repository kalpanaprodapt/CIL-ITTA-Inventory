import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { AMPLIFIER_AMP_TYPE_OPTIONS, AMPLIFIER_LIFECYCLE_STAGE_OPTIONS, AMPLIFIER_CRITICALITY_OPTIONS } from './types';
import type { AmplifierFilter } from './types';

interface Props {
  value: AmplifierFilter;
  onChange: (next: AmplifierFilter) => void;
  onReset: () => void;
}

export function AmplifierFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AmplifierFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Amplifier filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Amplifier"
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
      <FilterField label="System Id">
        <Input
          aria-label="System Id filter"
          placeholder="UUID"
          value={value.systemId ?? ''}
          onChange={(v) => patch({ systemId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Amp Type">
        <MultiSelect
          ariaLabel="Amp Type filter"
          options={AMPLIFIER_AMP_TYPE_OPTIONS}
          value={value.ampType ?? []}
          onChange={(v) => patch({ ampType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Gain Db">
        <RangeInputs
          ariaLabel="Gain Db"
          min={value.gainDbMin}
          max={value.gainDbMax}
          onChange={(min, max) => patch({ gainDbMin: min, gainDbMax: max })}
        />
      </FilterField>
      <FilterField label="Output Power Dbm">
        <RangeInputs
          ariaLabel="Output Power Dbm"
          min={value.outputPowerDbmMin}
          max={value.outputPowerDbmMax}
          onChange={(min, max) => patch({ outputPowerDbmMin: min, outputPowerDbmMax: max })}
        />
      </FilterField>
      <FilterField label="Noise Figure Db">
        <RangeInputs
          ariaLabel="Noise Figure Db"
          min={value.noiseFigureDbMin}
          max={value.noiseFigureDbMax}
          onChange={(min, max) => patch({ noiseFigureDbMin: min, noiseFigureDbMax: max })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={AMPLIFIER_LIFECYCLE_STAGE_OPTIONS}
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
          options={AMPLIFIER_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
